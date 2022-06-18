package com.tsu.slat.presentation.screens.nutrition

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.tsu.slat.data.api.Network
import com.tsu.slat.databinding.ActivityNutritionBinding
import com.tsu.slat.domain.usecases.GetNutritionUseCase
import com.tsu.slat.utils.api_utils.RequestBuilder
import com.tsu.slat.utils.RequestTestBuilder
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class NutritionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNutritionBinding

    private lateinit var viewModel: NutritionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNutritionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = NutritionViewModel()

        Network.createRepository(this)

        val useCase = GetNutritionUseCase(Network.nutritionRepository)


        /*
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
                    val params = RequestBuilder.getFoodByBarcode("802726425705")
                    useCase.findBarcode("802726425705", params)
                }
            }
        }*/

        //useCase.foods

    }
}