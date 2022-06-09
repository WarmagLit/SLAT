package com.tsu.slat.presentation.screens.foodsearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.tsu.slat.data.api.Network
import com.tsu.slat.databinding.ActivityFoodSearchBinding
import com.tsu.slat.domain.usecases.GetNutritionUseCase
import com.tsu.slat.utils.api_utils.RequestBuilder
import com.tsu.slat.utils.RequestTestBuilder
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class FoodSearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFoodSearchBinding

    private lateinit var viewModel: FoodSearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = FoodSearchViewModel()

        Network.createRepository(this)

        val useCase = GetNutritionUseCase(Network.nutritionRepository)

        binding.btnGetFoodById.setOnClickListener {
            runBlocking {
                launch {
                    val params = RequestBuilder.getFoodById("33691")
                    useCase.getFood("33691", params)
                    Log.d("taf",RequestTestBuilder.getFoodById("33691"))
                }
                println("Hello")
            }
        }

        binding.btnSearchFood.setOnClickListener {
            runBlocking {
                launch {
                    val params = RequestBuilder.findFood("Киндер")
                    useCase.findFood("Киндер", params)
                }
            }
        }

        //useCase.foods

    }
}