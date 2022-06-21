package com.tsu.slat.data.entity

import java.time.LocalDate

enum class Mealtime{
    BREAKFAST, LUNCH, DINNER, SNACK
}


data class DayMeal(
    val date: String = LocalDate.now().toString(),
    val type: Mealtime = Mealtime.BREAKFAST,
    val food: Food? = null
)
