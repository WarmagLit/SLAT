package com.tsu.slat.domain.usecases

import android.util.Log
import com.tsu.slat.data.entity.Food
import com.tsu.slat.data.entity.MealInfo
import com.tsu.slat.data.entity.MealInfoResponse
import com.tsu.slat.data.entity.SearchResponse
import com.tsu.slat.data.repository.NutritionRepository
import com.tsu.slat.utils.api_utils.RequestBuilder

class FindFoodInfoUseCase(private val repository: NutritionRepository) {

    var foodInfo: MealInfoResponse? = null

    suspend fun findFoodInfo(food_id: String) : MealInfoResponse? {
        val params = RequestBuilder.getFoodById(food_id)
        val res = repository.getNutrition(food_id, params)
        if (res.isSuccessful && res.body() != null) {
            foodInfo = res.body()
        } else {
            Log.d("Error", "Ошибка")
        }
        return foodInfo
    }
}