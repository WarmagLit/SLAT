package com.tsu.slat.domain.usecases

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class GetDayMeals {

    private var auth: FirebaseAuth = Firebase.auth
    private var db: FirebaseDatabase = Firebase.database


}