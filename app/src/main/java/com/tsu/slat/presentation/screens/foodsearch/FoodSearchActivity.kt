package com.tsu.slat.presentation.screens.foodsearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.tsu.slat.data.api.Network
import com.tsu.slat.data.repository.NutritionRemoteDataSourceImpl
import com.tsu.slat.data.repository.NutritionRepositoryImpl
import com.tsu.slat.databinding.ActivityFoodSearchBinding
import com.tsu.slat.databinding.ActivityMainBinding
import com.tsu.slat.domain.usecases.GetNutritionUseCase
import com.tsu.slat.utils.RequestBuilder
import com.tsu.slat.utils.RequestTestBuilder
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class FoodSearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFoodSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Network.createRepository(this)

        val useCase = GetNutritionUseCase(Network.nutritionRepository)

        binding.button2.setOnClickListener {
            runBlocking {
                launch {
                    val params = RequestBuilder.getFoodById("33691")
                    useCase.getFood("33691", params)
                    Log.d("taf",RequestTestBuilder.getFoodById("33691"))
                    //useCase.getFood("chicken")
                    //Log.d("taf",RequestBuilder.getFoodById("33691"))
                }
                println("Hello")
            }
        }

        binding.button3.setOnClickListener {
            runBlocking {
                launch {
                    val params = RequestBuilder.findFood("chicken")
                    useCase.findFood("chicken", params)
                    //Log.d("taf",RequestTestBuilder.getFoodById("33691"))
                    //useCase.getFood("chicken")
                    //Log.d("taf",RequestBuilder.getFoodById("33691"))
                }
            }
        }

        //useCase.foods

    }
}