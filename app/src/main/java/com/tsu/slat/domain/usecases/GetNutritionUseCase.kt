package com.tsu.slat.domain.usecases

import android.util.Log
import com.fatsecret.platform.model.CompactFood
import com.tsu.slat.data.repository.NutritionRepository
import com.tsu.slat.presentation.entity.OAuthQuery

class GetNutritionUseCase(private val repository: NutritionRepository) {

    lateinit var foods: CompactFood

    suspend fun getFood(food_id: String, params: OAuthQuery)  {
        val res = repository.getNutrition(food_id, params)
        if (res.isSuccessful && res.body() != null) {
            foods = res.body()!!
        } else {
            Log.d("Err", "Ошибка")
        }
    }

    suspend fun findFood(food: String, params: OAuthQuery)  {
        val res = repository.findFood(food, params)
        if (res.isSuccessful && res.body() != null) {
            foods = res.body()!!
        } else {
            Log.d("Err", "Ошибка")
        }
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