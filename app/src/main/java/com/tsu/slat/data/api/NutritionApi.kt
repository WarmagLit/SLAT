package com.tsu.slat.data.api

import com.fatsecret.platform.model.CompactFood
import com.tsu.slat.data.entity.Foods
import retrofit2.Response
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NutritionApi {
    @GET("server.api")
    suspend fun getNutrition(@Query(value = "food_id") food_id: String,
                             @Query(value = "format") format: String,
                             @Query(value = "method") method: String,
                             @Query(value = "oauth_consumer_key") oauth_consumer_key: String,
                             @Query(value = "oauth_nonce") oauth_nonce: String,
                             @Query(value = "oauth_signature") oauth_signature: String,
                             @Query(value = "oauth_signature_method") oauth_signature_method: String,
                             @Query(value = "oauth_timestamp") oauth_timestamp: String,
                             @Query(value = "oauth_version") version: String): Response<CompactFood>
}