package com.tsu.slat.domain.usecases

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthorizeUseCase {
    // Firebase instance variables
    private var auth: FirebaseAuth

    init {
        auth = Firebase.auth
    }
}