package com.tsu.slat.presentation.screens.add_measure

import androidx.lifecycle.ViewModel
import com.tsu.slat.data.entity.BodyMeasure
import com.tsu.slat.data.entity.Workout
import com.tsu.slat.data.repository.BodyParamsRepository
import com.tsu.slat.data.repository.TrainingRepository

class AddMeasureViewModel: ViewModel() {

    private val repository = BodyParamsRepository()

    fun addMeasure(bodyMeasure: BodyMeasure) {
        repository.addMeasure(bodyMeasure)
    }
}