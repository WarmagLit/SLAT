package com.tsu.slat.data.entity.response

import com.tsu.slat.data.entity.Chat

data class ChatResponse(
    val chat: Chat? = null,
    val lastMessage: MessageResponse? = null
)
