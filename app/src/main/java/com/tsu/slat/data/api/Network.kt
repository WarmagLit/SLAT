package com.tsu.slat.data.api

import android.content.Context
import com.google.gson.GsonBuilder
import com.tsu.slat.data.repository.NutritionRemoteDataSourceImpl
import com.tsu.slat.data.repository.NutritionRepository
import com.tsu.slat.data.repository.NutritionRepositoryImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object Network {

    var BASE_URL = "https://platform.fatsecret.com/rest/"

    lateinit var nutritionRepository: NutritionRepository

    lateinit var retrofit: Retrofit

    fun createRepository(context: Context) {
        configureRetrofit()

        //val wordsDao = Database.getDatabase(context).wordsDao()

        nutritionRepository = NutritionRepositoryImpl(
            NutritionRemoteDataSourceImpl(retrofit.create())
        )
    }

    private fun configureRetrofit() {
        val okHttpClient =  buildHttpClient()
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    private fun buildHttpClient(): OkHttpClient {
        val interceptor = buildInterceptor()
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    private fun buildInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

}