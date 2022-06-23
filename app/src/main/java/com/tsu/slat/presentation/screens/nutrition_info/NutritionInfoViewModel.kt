package com.tsu.slat.presentation.screens.nutrition_info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tsu.slat.data.entity.*
import com.tsu.slat.data.entity.response.MealInfoResponse
import com.tsu.slat.data.repository.DayMealRepository
import com.tsu.slat.domain.usecases.FindFoodInfoUseCase
import kotlinx.coroutines.launch
import java.time.LocalDate

class NutritionInfoViewModel(private val useCase: FindFoodInfoUseCase): ViewModel() {

    private val repository = DayMealRepository()

    val mealInfo = MutableLiveData<MealInfoResponse>()

    fun addMeal(date: LocalDate, type: Mealtime, food: Food) {
        repository.addMeal(date, type, food)
    }

    fun findFoodInfo(foodItemShort: FoodItemShort) {
        viewModelScope.launch {
           val result = useCase.findFoodInfo(foodItemShort.food_id.toString())
            mealInfo.value = result
        }
    }
}