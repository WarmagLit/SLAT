package com.tsu.slat.data.entity

import com.tsu.itindr.MainScreen.ui.chats.Chat

data class ChatResponse(
    val chat: Chat,
    val lastMessage: MessageResponse?
)
