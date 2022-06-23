package com.tsu.slat.presentation.screens.client_menu.ui.chats

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.tsu.slat.data.entity.Chat
import com.tsu.slat.data.entity.response.ChatResponse
import com.tsu.slat.databinding.FragmentChatsBinding

class ChatsFragment : Fragment(), ChatAdapter.OnItemClickListener {

    private val chatsViewModel by viewModels<ChatsViewModel>()
    private var _binding: FragmentChatsBinding? = null
    private lateinit var adapter: ChatAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // Firebase instance variables
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(ChatsViewModel::class.java)

        _binding = FragmentChatsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initAdapter()

        // Initialize Firebase Auth and check if the user is signed in
        auth = Firebase.auth

        db = Firebase.database

        loadChats()

        return root
    }

    private fun initAdapter() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = ChatAdapter(requireContext(), this)
        binding.recyclerView.adapter = adapter
    }

    private fun loadChats() {
        val user = auth.currentUser
        val chatsIdRef = db.reference.child(USERS_CHILD).child(user!!.uid).child("chats_id")
        Log.d("tag", chatsIdRef.toString())
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                for (postSnapshot in dataSnapshot.children) {
                    val chat = postSnapshot.getValue<ChatResponse>()
                    val responce = chat ?: ChatResponse(Chat("null", "null", "null"),  null)
                    adapter.addChat(responce)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("tag", "loadPost:onCancelled", databaseError.toException())
            }
        }
        chatsIdRef.addValueEventListener(postListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(userId: String) {
        Log.d("Chats", "Chat CLICKED")
    }

    companion object {
        const val CHATS_CHILD = "chats"
        const val USERS_CHILD = "users"
    }
}