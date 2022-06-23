package com.tsu.slat.presentation.screens.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tsu.slat.data.entity.CalendarStorableEvent
import com.tsu.slat.data.repository.CalendarRepository
import com.tsu.slat.presentation.entity.CalendarEvent
import java.time.LocalDate

class CalendarViewModel: ViewModel() {

    val events: LiveData<Map<LocalDate, List<CalendarEvent>>>
    val repository = CalendarRepository()

    init {
        events = repository.eventsLive
    }

    fun addEventToDatabase(date: LocalDate, event: CalendarEvent) {
        val storableEvent = CalendarStorableEvent(event.id, event.text, event.date.toString())
        repository.addEvent(date, storableEvent)
    }

    fun deleteEvent(event: CalendarEvent) {
        repository.deleteEvent(event)
    }
}