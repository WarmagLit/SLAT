package com.tsu.slat.domain.usecases

import android.util.Log
import com.fatsecret.platform.model.CompactFood
import com.tsu.slat.data.entity.FindFoodResponse
import com.tsu.slat.data.entity.MealInfo
import com.tsu.slat.data.entity.MealInfoResponse
import com.tsu.slat.data.entity.SearchResponse
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