package com.tsu.slat.presentation.screens.chat

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.codelab.friendlychat.MyButtonObserver
import com.google.firebase.codelab.friendlychat.MyScrollToBottomObserver
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.tsu.slat.R
import com.tsu.slat.data.entity.MessageResponse
import com.tsu.slat.data.entity.UserMessageInfo
import com.tsu.slat.databinding.ActivityChatBinding
import com.tsu.slat.domain.usecases.GetChatUseCase
import com.tsu.slat.presentation.screens.sign_in.SignInActivity
import java.util.*

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var manager: LinearLayoutManager


    // Firebase instance variables
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseDatabase
    private lateinit var adapter: FriendlyMessageAdapter

    private lateinit var userInfo: UserMessageInfo

    private lateinit var chatViewModel: ChatViewModel

    private lateinit var openDocument: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val chatId = intent.getStringExtra("chatId").toString()

        chatViewModel = ChatViewModel(GetChatUseCase(), chatId)

        openDocument = registerForActivityResult(MyOpenDocumentContract()) { uri ->
            chatViewModel.onImageSelected(uri!!)
        }

        // Initialize Firebase Auth and check if the user is signed in
        auth = Firebase.auth
        if (auth.currentUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
            return
        }

        // Initialize Realtime Database
        db = Firebase.database

        getUserMessageInfo()

        binding.progressBar.visibility = ProgressBar.INVISIBLE

        initRecycler()

        // Scroll down when a new message arrives
        // See MyScrollToBottomObserver for details
        adapter.registerAdapterDataObserver(
            MyScrollToBottomObserver(binding.messageRecyclerView, adapter, manager)
        )

        setListeners()
    }

    private fun initRecycler() {
        val chatId = intent.getStringExtra("chatId").toString()
        val messagesRef = db.reference.child(CHATS_CHILD).child(chatId)
        val options = FirebaseRecyclerOptions.Builder<MessageResponse>()
            .setQuery(messagesRef, MessageResponse::class.java)
            .build()
        adapter = FriendlyMessageAdapter(options, getUserName())

        manager = LinearLayoutManager(this)
        manager.stackFromEnd = true
        binding.messageRecyclerView.layoutManager = manager
        binding.messageRecyclerView.adapter = adapter
        binding.messageRecyclerView.itemAnimator = null
    }

    private fun setListeners() {
        // Disable the send button when there's no text in the input field
        // See MyButtonObserver for details
        binding.messageEditText.addTextChangedListener(MyButtonObserver(binding.sendButton))

        // When the send button is clicked, send a text message
        binding.sendButton.setOnClickListener {
            chatViewModel.sendMessage(binding.messageEditText.text.toString())
            binding.messageEditText.setText("")
        }

        // When the image button is clicked, launch the image picker
        binding.addMessageImageView.setOnClickListener {
            openDocument.launch(arrayOf("image/*"))
        }

    }
    private fun getUserMessageInfo() {
        val uid = auth.currentUser?.uid
        val name = auth.currentUser?.displayName
        val photo = getPhotoUrl()

        userInfo = UserMessageInfo(uid, name, photo)
    }


    public override fun onStart() {
        super.onStart()
        // Check if user is signed in.
        if (auth.currentUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
            return
        }
    }

    public override fun onPause() {
        adapter.stopListening()
        super.onPause()
    }

    public override fun onResume() {
        super.onResume()
        adapter.startListening()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sign_out_menu -> {
                signOut()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun signOut() {
        AuthUI.getInstance().signOut(this)
        startActivity(Intent(this, SignInActivity::class.java))
        finish()
    }

    private fun getPhotoUrl(): String? {
        val user = auth.currentUser
        return user?.photoUrl?.toString()
    }

    private fun getUserName(): String? {

        val user = auth.currentUser

        return if (user != null) {
            user.displayName
        } else ANONYMOUS
    }

    companion object {
        private const val TAG = "MainActivity"
        const val MESSAGES_CHILD = "messages"
        const val CHATS_CHILD = "chats"
        const val ANONYMOUS = "anonymous"
        private const val LOADING_IMAGE_URL = "https://www.google.com/images/spin-32.gif"
    }
}