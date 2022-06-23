package com.tsu.slat.domain.usecases

import android.util.Log
import com.tsu.slat.data.entity.response.MealInfoResponse
import com.tsu.slat.data.entity.response.SearchResponse
import com.tsu.slat.data.repository.NutritionRepository
import com.tsu.slat.presentation.entity.OAuthQuery

class GetNutritionUseCase(private val repository: NutritionRepository) {


    lateinit var foods: MealInfoResponse

    var findFood: SearchResponse? = null

    suspend fun getFood(food_id: String, params: OAuthQuery): MealInfoResponse?  {
        val res = repository.getNutrition(food_id, params)
        if (res.isSuccessful && res.body() != null) {
            foods = res.body()!!
            return res.body()!!
        } else {
            Log.d("Err", "Ошибка")
        }
        return null
    }

    suspend fun findBarcode(barcode: String, params: OAuthQuery)  {
        val res = repository.findBarcode(barcode, params)
        if (res.isSuccessful && res.body() != null) {
            //foods = res.body()!!
        } else {
            Log.d("Err", "Ошибка")
        }
    }
}