package com.tsu.slat.presentation.screens.train_parameters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tsu.slat.data.entity.Role
import com.tsu.slat.data.entity.Workout
import com.tsu.slat.data.repository.TrainingRepository
import com.tsu.slat.presentation.screens.sign_in.SignInRepository

class TrainParametersViewModel: ViewModel() {

    private val repository = TrainingRepository()

    val workouts: LiveData<List<Workout>> = repository.workoutsLive

    /*
    private val _intentToMenu = MutableLiveData<Boolean>()
    val intentToMenu: LiveData<Boolean>
        get() = _intentToMenu*/


}