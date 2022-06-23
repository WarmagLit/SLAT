package com.tsu.slat.presentation.screens.chat

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tsu.slat.domain.usecases.GetChatUseCase

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