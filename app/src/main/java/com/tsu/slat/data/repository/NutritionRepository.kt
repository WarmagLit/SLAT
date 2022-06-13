package com.tsu.slat.data.repository

import com.fatsecret.platform.model.CompactFood
import com.tsu.slat.data.entity.BarcodeResponse
import com.tsu.slat.presentation.screens.foodsearch.OAuthQuery
import retrofit2.Response
import retrofit2.http.Query

interface NutritionRepository {
    suspend fun getNutrition(food_id: String, params: OAuthQuery):  Response<CompactFood>
    suspend fun findFood(food: String, params: OAuthQuery) : Response<CompactFood>
    suspend fun findBarcode(barcode: String, params: OAuthQuery): Response<BarcodeResponse>
}