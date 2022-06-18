package com.tsu.slat.presentation.screens.calendar

import java.time.LocalDate

data class CalendarEvent(
    val id: String,
    val text: String,
    val date: LocalDate
    )
