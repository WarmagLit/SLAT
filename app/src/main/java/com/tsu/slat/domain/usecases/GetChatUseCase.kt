package com.tsu.slat.domain.usecases

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.tsu.slat.data.entity.MessageResponse
import com.tsu.slat.data.entity.UserMessageInfo
import com.tsu.slat.presentation.screens.chat.ChatActivity
import java.util.*

class GetChatUseCase {

    private var auth: FirebaseAuth = Firebase.auth
    private var db: FirebaseDatabase = Firebase.database

    private var userInfo: UserMessageInfo

    init {
        val uid = auth.currentUser?.uid
        val name = auth.currentUser?.displayName
        val photo = auth.currentUser?.photoUrl.toString()

        userInfo = UserMessageInfo(uid, name, photo)
    }


    fun sendMessage(text: String, chatId: String) {
        val friendlyMessage = MessageResponse(
            "iddddd",
            text,
            Date().time.toString(),
            userInfo,
            null
        )
        db.reference.child(ChatActivity.CHATS_CHILD).child(chatId).push().setValue(friendlyMessage)
    }

    fun onImageSelected(uri: Uri, chatId: String) {
        val user = auth.currentUser
        val tempMessage =  MessageResponse(
            "iddddd",
            null,
            Date().time.toString(),
            userInfo,
            LOADING_IMAGE_URL
        )
        db.reference
            .child(ChatActivity.CHATS_CHILD)
            .child(chatId)
            .push()
            .setValue(
                tempMessage,
                DatabaseReference.CompletionListener { databaseError, databaseReference ->
                    if (databaseError != null) {
                        Log.w(
                            TAG, "Unable to write message to database.",
                            databaseError.toException()
                        )
                        return@CompletionListener
                    }

                    // Build a StorageReference and then upload the file
                    val key = databaseReference.key
                    val storageReference = Firebase.storage
                        .getReference(user!!.uid)
                        .child(key!!)
                        .child(uri.lastPathSegment!!)
                    putImageInStorage(storageReference, uri, key, chatId)
                })
    }

    fun putImageInStorage(storageReference: StorageReference, uri: Uri, key: String?, chatId: String) {

        // First upload the image to Cloud Storage
        val uploadTask = storageReference.putFile(uri)
        uploadTask.addOnSuccessListener {
                taskSnapshot -> // After the image loads, get a public downloadUrl for the image
            // and add it to the message.
            taskSnapshot.metadata!!.reference!!.downloadUrl
                .addOnSuccessListener { uri ->
                    val friendlyMessage = MessageResponse(
                        "iddddd",
                        null,
                        Date().time.toString(),
                        userInfo,
                        uri.toString()
                    )
                    db.reference
                        .child(ChatActivity.CHATS_CHILD)
                        .child(chatId)
                        .child(key!!)
                        .setValue(friendlyMessage)
                }
        }.addOnFailureListener {
            Log.w(
                "Tag",
                "Image upload task was unsuccessful."
            )
        }
    }



    companion object {
        private const val TAG = "ChatUseCase"
        const val MESSAGES_CHILD = "messages"
        const val CHATS_CHILD = "chats"
        const val ANONYMOUS = "anonymous"
        private const val LOADING_IMAGE_URL = "https://www.google.com/images/spin-32.gif"
    }
}