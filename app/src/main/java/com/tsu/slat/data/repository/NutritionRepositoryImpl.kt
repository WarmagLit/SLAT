package com.tsu.slat.data.repository

import com.fatsecret.platform.model.CompactFood
import com.tsu.slat.data.entity.BarcodeResponse
import com.tsu.slat.data.entity.FindFoodResponse
import com.tsu.slat.data.entity.MealInfo
import com.tsu.slat.data.entity.SearchResponse
import com.tsu.slat.presentation.entity.OAuthQuery
import retrofit2.Response

class NutritionRepositoryImpl(private val nutritionDatasource: NutritionRemoteDataSource): NutritionRepository {
    override suspend fun getNutrition(food_id: String, params: OAuthQuery): Response<MealInfo> {
        return nutritionDatasource.getFoodById(food_id, params)
    }

    override suspend fun findFood(food: String, params: OAuthQuery): Response<SearchResponse> {
        return nutritionDatasource.findFood(food, params)
    }

    override suspend fun findBarcode(barcode: String, params: OAuthQuery): Response<BarcodeResponse> {
        return nutritionDatasource.findBarcode(barcode, params)
    }
}