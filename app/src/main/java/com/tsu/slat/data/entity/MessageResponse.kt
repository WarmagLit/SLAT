package com.tsu.slat.data.entity

data class MessageResponse(
    val id: String,
    val text: String?,
    val createdAt: String,
    val user: String,
    val attachments: List<String>
)