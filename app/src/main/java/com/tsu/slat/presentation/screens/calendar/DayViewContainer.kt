package com.tsu.slat.presentation.screens.calendar

import android.view.View
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.ViewContainer
import com.tsu.slat.databinding.CalendarDayLayoutBinding

class DayViewContainer(view: View) : ViewContainer(view) {

    // With ViewBinding

    // Will be set when this container is bound
    lateinit var day: CalendarDay

    init {
        view.setOnClickListener {
            // Use the CalendarDay associated with this container.

        }
    }
}