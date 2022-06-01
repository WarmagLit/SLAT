package com.tsu.slat.presentation.screens.chat

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.storage.StorageReference
import com.tsu.slat.data.entity.MessageResponse
import com.tsu.slat.domain.usecases.GetChatUseCase
import java.util.*
import java.util.concurrent.Executor

class ChatViewModel(private val getChatUseCase: GetChatUseCase, private val chatId: String) : ViewModel() {



    fun sendMessage(text: String) {
        getChatUseCase.sendMessage(text, chatId)
    }

    fun onImageSelected(uri: Uri) {
        getChatUseCase.onImageSelected(uri, chatId)
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is chats Fragment"
    }
    val text: LiveData<String> = _text



}