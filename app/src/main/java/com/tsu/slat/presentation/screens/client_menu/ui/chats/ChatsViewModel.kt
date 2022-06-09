package com.tsu.slat.presentation.screens.client_menu.ui.chats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChatsViewModel : ViewModel() {


    private val _text = MutableLiveData<String>().apply {
        value = "This is chats Fragment"
    }
    val text: LiveData<String> = _text


}