package com.tsu.slat.data.repository

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
import com.tsu.slat.data.entity.CalendarStorableEvent
import com.tsu.slat.data.entity.Workout
import com.tsu.slat.presentation.entity.CalendarEvent
import java.time.LocalDate

class TrainingRepository {

    private val auth: FirebaseAuth = Firebase.auth
    private val database = Firebase.database
    private val myRef: DatabaseReference = database.getReference("users").child(getCurrentUserId()).child("training")

    private val _workoutsLive =  MutableLiveData<List<Workout>>()


    val workoutsLive: LiveData<List<Workout>>
        get() = _workoutsLive

    init {
        getEvents()
    }

    private fun getEvents() {
        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val storedWorkouts = mutableListOf<Workout>()

                for (postSnapshot in dataSnapshot.children) {
                    val value = postSnapshot.getValue<Workout>()
                    val timestamp = value?.timestamp ?: 0
                    storedWorkouts.add(Workout(value?.id.toString(), value?.name.toString(), value?.date.toString(), timestamp, value?.amount, value?.description))
                }

                if (storedWorkouts.isNotEmpty()) {
                    storedWorkouts.sortBy {
                        it.timestamp
                    }
                    _workoutsLive.value = storedWorkouts
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                //Log.w("TAG", "Failed to read value.", error.toException())
            }
        })
    }

    fun addWorkout(workout: Workout) {
        myRef.child(workout.id).setValue(workout)
    }

    fun deleteWorkout(workout: Workout) {
        myRef.child(workout.id).removeValue()
    }

    private fun getCurrentUserId(): String = auth.currentUser?.uid.toString()
}