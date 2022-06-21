package com.tsu.slat.presentation.screens.find_food

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.tsu.slat.data.api.Network
import com.tsu.slat.data.entity.Mealtime
import com.tsu.slat.databinding.ActivityFindFoodBinding
import com.tsu.slat.domain.usecases.FindFoodsUseCase
import com.tsu.slat.domain.usecases.GetNutritionUseCase
import com.tsu.slat.presentation.screens.nutrition_info.NutritionInfoActivity
import com.tsu.slat.utils.api_utils.RequestBuilder
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDate

class FindFoodActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFindFoodBinding

    private lateinit var viewModel: FindFoodViewModel

    private lateinit var adapter: FindFoodAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Network.createRepository(this)

        viewModel = FindFoodViewModel(FindFoodsUseCase(Network.nutritionRepository))

        adapter = viewModel.adapter

        val useCase = GetNutritionUseCase(Network.nutritionRepository)

        initRecycler()

        initListeners()
        initObservers()

        /*
        binding.btnGetFoodById.setOnClickListener {
            runBlocking {
                launch {
                    val params = RequestBuilder.getFoodById("33691")
                    val meal = useCase.getFood("33691", params)

                    val intent = Intent(this@FindFoodActivity, NutritionInfoActivity::class.java)
                    intent.putExtra("MEAL_INFO", meal)
                    intent.putExtra("meal_type", mealType)
                    intent.putExtra("date", addMealDate)
                    startActivity(intent)

                    Log.d("taf",meal.toString())
                    //Log.d("taf",RequestTestBuilder.getFoodById("33691"))
                }
                println("Hello")
            }
        }*/

        binding.btnSearchFood.setOnClickListener {
            val res = viewModel.getFoods("Mountain")
            Log.d("resss",res.toString())
        }

    }

    private fun initRecycler() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun initListeners() {
        binding.textInputSearch.setEndIconOnClickListener {
            val foodName = binding.textSearch.text.toString()
            viewModel.getFoods(foodName)
        }
    }

    private fun initObservers() {
        val addMealDate = intent.getSerializableExtra("date") as LocalDate
        val mealtime =  intent.getSerializableExtra("mealtime") as Mealtime

        viewModel.selectedFood.observe(this) {
            Log.d("observer", "Works")
            val intent = Intent(this@FindFoodActivity, NutritionInfoActivity::class.java)
            intent.putExtra("FOOD_ITEM_SHORT", it)
            intent.putExtra("mealtime", mealtime)
            intent.putExtra("date", addMealDate)
            startActivity(intent)
        }
    }
}