package com.tsu.slat.data.repository

import com.tsu.slat.data.entity.response.BarcodeResponse
import com.tsu.slat.data.entity.response.MealInfoResponse
import com.tsu.slat.data.entity.response.SearchResponse
import com.tsu.slat.presentation.entity.OAuthQuery
import retrofit2.Response

class NutritionRepositoryImpl(private val nutritionDatasource: NutritionRemoteDataSource): NutritionRepository {
    override suspend fun getNutrition(food_id: String, params: OAuthQuery): Response<MealInfoResponse> {
        return nutritionDatasource.getFoodById(food_id, params)
    }

    override suspend fun findFood(food: String, params: OAuthQuery): Response<SearchResponse> {
        return nutritionDatasource.findFood(food, params)
    }

    override suspend fun findBarcode(barcode: String, params: OAuthQuery): Response<BarcodeResponse> {
        return nutritionDatasource.findBarcode(barcode, params)
    }
}