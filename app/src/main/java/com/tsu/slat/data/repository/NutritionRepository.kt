package com.tsu.slat.data.repository

import com.tsu.slat.data.entity.BarcodeResponse
import com.tsu.slat.data.entity.FindFoodResponse
import com.tsu.slat.data.entity.MealInfo
import com.tsu.slat.data.entity.SearchResponse
import com.tsu.slat.presentation.entity.OAuthQuery
import retrofit2.Response

interface NutritionRepository {
    suspend fun getNutrition(food_id: String, params: OAuthQuery):  Response<MealInfo>
    suspend fun findFood(food: String, params: OAuthQuery) : Response<SearchResponse>
    suspend fun findBarcode(barcode: String, params: OAuthQuery): Response<BarcodeResponse>
}