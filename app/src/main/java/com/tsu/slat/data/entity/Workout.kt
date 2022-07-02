package com.tsu.slat.data.entity

import java.sql.Timestamp

data class Workout(
    val id: String = "",
    val name: String = "",
    val date: String = "",
    val timestamp: Int = 0,
    val amount: Double? = null,
    val description: String? = null
)
