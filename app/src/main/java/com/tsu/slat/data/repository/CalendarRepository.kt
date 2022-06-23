package com.tsu.slat.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.tsu.slat.data.entity.*
import com.tsu.slat.presentation.entity.CalendarEvent
import java.time.LocalDate

class CalendarRepository {

    private val auth: FirebaseAuth = Firebase.auth
    private val database = Firebase.database
    private val myRef: DatabaseReference = database.getReference("users").child(getCurrentUserId()).child("calendar")

    private val _eventsLive =  MutableLiveData<Map<LocalDate, List<CalendarEvent>>>()


    val eventsLive: LiveData<Map<LocalDate, List<CalendarEvent>>>
        get() = _eventsLive

    init {
        getEvents()
    }

    private fun getEvents() {
        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val storedEvents = mutableMapOf<LocalDate, List<CalendarEvent>>()

                for (postSnapshot in dataSnapshot.children) {
                    for (event in postSnapshot.children) {
                        val value = event.getValue<CalendarStorableEvent>()
                        if (value != null) {
                            val eventDate = LocalDate.parse(value.date)
                            if (storedEvents[eventDate] != null)
                                storedEvents[eventDate] = storedEvents[eventDate]!!.plus(CalendarEvent(value.id, value.text, eventDate))
                            else {
                                storedEvents[eventDate] = listOf(CalendarEvent(value.id, value.text, eventDate))
                            }
                        }
                    }
                }

                if (storedEvents.isNotEmpty()) {
                   _eventsLive.value = storedEvents
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                //Log.w("TAG", "Failed to read value.", error.toException())
            }
        })
    }

    fun addEvent(date: LocalDate, event: CalendarStorableEvent) {
        myRef.child(date.toString()).child(event.id).setValue(event)
    }

    fun deleteEvent(event: CalendarEvent) {
        myRef.child(event.date.toString()).child(event.id).removeValue()
    }

    private fun getCurrentUserId(): String = auth.currentUser?.uid.toString()
}