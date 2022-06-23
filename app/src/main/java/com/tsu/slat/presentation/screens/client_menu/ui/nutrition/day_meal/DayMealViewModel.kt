package com.tsu.slat.presentation.screens.client_menu.ui.nutrition.day_meal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tsu.slat.data.entity.DayMeal
import com.tsu.slat.data.repository.DayMealRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DayMealViewModel: ViewModel() {

    private val repository = DayMealRepository()

    val dayCalories = MutableLiveData<Int>()

    private val _dayMeals = MutableLiveData<List<DayMeal>>()
    val dayMeals: LiveData<List<DayMeal>>
        get() = _dayMeals

    init {
        viewModelScope.launch {
            repository.mealsByDayList.collect {
                _dayMeals.value = it
            }
        }

        dayCalories.value = 0
    }
}