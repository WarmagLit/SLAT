package com.tsu.slat.data.entity

data class MessageResponse(
    var id: String? = null,
    var text: String?  = null,
    var createdAt: String? = null,
    var userInfo: UserMessageInfo?  = null,
    var attachments: String?  = null

)