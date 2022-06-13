package com.tsu.slat.data.repository

import com.fatsecret.platform.model.CompactFood
import com.tsu.slat.data.entity.BarcodeResponse
import com.tsu.slat.presentation.screens.foodsearch.OAuthQuery
import retrofit2.Response

class NutritionRepositoryImpl(private val nutritionDatasource: NutritionRemoteDataSource): NutritionRepository {
    override suspend fun getNutrition(food_id: String, params: OAuthQuery): Response<CompactFood> {
        return nutritionDatasource.getFoodById(food_id, params)
    }

    override suspend fun findFood(food: String, params: OAuthQuery): Response<CompactFood> {
        return nutritionDatasource.findFood(food, params)
    }

    override suspend fun findBarcode(barcode: String, params: OAuthQuery): Response<BarcodeResponse> {
        return nutritionDatasource.findBarcode(barcode, params)
    }
}