package com.tsu.slat.presentation.screens.body_params_info

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tsu.slat.data.entity.BodyMeasure
import com.tsu.slat.data.entity.Workout
import com.tsu.slat.data.repository.BodyParamsRepository
import com.tsu.slat.data.repository.TrainingRepository
import com.tsu.slat.presentation.screens.train_parameters.TrainAdapter

class BodyParamsInfoViewModel: ViewModel() {

    private val repository = BodyParamsRepository()

    val measures: LiveData<List<BodyMeasure>> = repository.paramsLive



}