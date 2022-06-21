package com.tsu.slat.domain.usecases

import android.util.Log
import com.tsu.slat.data.entity.SearchResponse
import com.tsu.slat.data.repository.NutritionRepository
import com.tsu.slat.presentation.entity.OAuthQuery
import com.tsu.slat.utils.api_utils.RequestBuilder

class FindFoodsUseCase(private val repository: NutritionRepository) {

    var findFood: SearchResponse? = null

    suspend fun findFood(food: String) : SearchResponse? {
        val params = RequestBuilder.findFood(food)
        val res = repository.findFood(food, params)
        if (res.isSuccessful && res.body() != null) {
            findFood = res.body()
        } else {
            Log.d("Error", "Ошибка")
        }
        return findFood
    }
}