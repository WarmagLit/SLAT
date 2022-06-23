package com.tsu.slat.presentation.screens.client_menu.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ProfileViewModel : ViewModel() {

    val profileName = MutableLiveData<String>()
    val profileImageUrl = MutableLiveData<String>()

    private var auth: FirebaseAuth = Firebase.auth
    private var db: FirebaseDatabase = Firebase.database

    init {
        profileName.value = auth.currentUser?.displayName
        profileImageUrl.value = auth.currentUser?.photoUrl.toString()
    }

}