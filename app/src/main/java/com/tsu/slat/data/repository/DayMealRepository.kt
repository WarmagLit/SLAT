package com.tsu.slat.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.tsu.slat.data.entity.DayMeal
import com.tsu.slat.data.entity.Food
import com.tsu.slat.data.entity.Mealtime
import com.tsu.slat.presentation.entity.CalendarEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate

class DayMealRepository {

    private val auth: FirebaseAuth = Firebase.auth
    private val database = Firebase.database
    private val myRef: DatabaseReference = database.getReference("users").child(getCurrentUserId()).child("meals")

    var mealsByDay: List<DayMeal>? = null

    val mealsByDayList: Flow<List<DayMeal>> = flow {
        while (true) {
            mealsByDay?.let {
                val latest = it
                emit(latest)
            }
            delay(1000)
        }
    }

    init {
        getMeals()
    }

    private fun getMeals() {
        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val meals = mutableListOf<DayMeal>()

                for (postSnapshot in dataSnapshot.children) {
                    val value = postSnapshot.getValue<DayMeal>()
                    if (value != null) {
                        meals.add(value)
                    }
                }

                if (meals.isNotEmpty()) {
                    mealsByDay = meals
                }

            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                //Log.w("TAG", "Failed to read value.", error.toException())
            }
        })
    }

    fun addMeal(date: LocalDate, type: Mealtime, food: Food) {
        val meal = DayMeal(date.toString(), type, food)
        myRef.push().setValue(meal)
    }

    private fun getCurrentUserId(): String = auth.currentUser?.uid.toString()
}