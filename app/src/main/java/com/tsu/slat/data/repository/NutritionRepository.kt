package com.tsu.slat.data.repository

import com.fatsecret.platform.model.CompactFood
import com.tsu.slat.data.entity.Foods
import com.tsu.slat.data.entity.NutrientsResponse
import com.tsu.slat.presentation.screens.foodsearch.OAuthQuery
import retrofit2.Response

interface NutritionRepository {
    suspend fun getNutrition(food_id: String, params: OAuthQuery):  Response<CompactFood>
}