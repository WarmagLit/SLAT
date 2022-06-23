package com.tsu.slat.data.entity.response

import com.tsu.slat.data.entity.UserMessageInfo

data class MessageResponse(
    var id: String? = null,
    var text: String?  = null,
    var createdAt: String? = null,
    var userInfo: UserMessageInfo?  = null,
    var attachments: String?  = null

)