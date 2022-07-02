package com.tsu.slat.presentation.screens.add_workout

import androidx.lifecycle.ViewModel
import com.tsu.slat.data.entity.Workout
import com.tsu.slat.data.repository.TrainingRepository

class AddWorkoutViewModel: ViewModel() {

    private val repository = TrainingRepository()

    //val workouts: LiveData<List<Workout>> = repository.workoutsLive

    /*
    private val _intentToMenu = MutableLiveData<Boolean>()
    val intentToMenu: LiveData<Boolean>
        get() = _intentToMenu*/

    fun addWorkout(workout: Workout) {
        repository.addWorkout(workout)
    }
}