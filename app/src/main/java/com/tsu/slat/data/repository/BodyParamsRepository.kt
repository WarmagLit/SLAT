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
import com.tsu.slat.data.entity.BodyMeasure

class BodyParamsRepository {

    private val auth: FirebaseAuth = Firebase.auth
    private val database = Firebase.database
    private val myRef: DatabaseReference = database.getReference("users").child(getCurrentUserId()).child("body_measures")

    private val _measuresLive =  MutableLiveData<List<BodyMeasure>>()


    val paramsLive: LiveData<List<BodyMeasure>>
        get() = _measuresLive

    init {
        getEvents()
    }

    private fun getEvents() {
        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val storedMeasures = mutableListOf<BodyMeasure>()

                for (postSnapshot in dataSnapshot.children) {
                    val value = postSnapshot.getValue<BodyMeasure>()
                    val timestamp = value?.timestamp ?: 0
                    val res = value?.value?: 0.0
                    storedMeasures.add(BodyMeasure(value?.id.toString(), value?.date.toString(), timestamp, res))
                }

                if (storedMeasures.isNotEmpty()) {
                    storedMeasures.sortBy {
                        it.timestamp
                    }
                    _measuresLive.value = storedMeasures
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                //Log.w("TAG", "Failed to read value.", error.toException())
            }
        })
    }

    fun addMeasure(bodyMeasure: BodyMeasure) {
        myRef.child(bodyMeasure.id).setValue(bodyMeasure)
    }

    fun deleteMeasure(bodyMeasure: BodyMeasure) {
        myRef.child(bodyMeasure.id).removeValue()
    }

    private fun getCurrentUserId(): String = auth.currentUser?.uid.toString()
}