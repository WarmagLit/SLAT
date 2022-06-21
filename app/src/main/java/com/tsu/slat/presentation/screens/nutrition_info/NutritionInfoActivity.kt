package com.tsu.slat.presentation.screens.nutrition_info

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.tsu.slat.data.api.Network
import com.tsu.slat.data.entity.FoodItemShort
import com.tsu.slat.data.entity.MealInfo
import com.tsu.slat.data.entity.Mealtime
import com.tsu.slat.databinding.ActivityNutritionInfoBinding
import com.tsu.slat.domain.usecases.FindFoodInfoUseCase
import com.tsu.slat.presentation.screens.client_menu.ClientMenuActivity
import java.time.LocalDate

class NutritionInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNutritionInfoBinding

    private var foodItem: FoodItemShort? = null
    private var currentMeal: MealInfo? = null
    private var addMealDate: LocalDate = LocalDate.now()
    private var mealType: Mealtime = Mealtime.BREAKFAST

    private lateinit var nutritionInfoViewModel: NutritionInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNutritionInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        foodItem = intent.getParcelableExtra<FoodItemShort>("FOOD_ITEM_SHORT")
        addMealDate = intent.getSerializableExtra("date") as LocalDate
        mealType = intent.getSerializableExtra("mealtime") as Mealtime

        Network.createRepository(this)

        val useCase = FindFoodInfoUseCase( Network.nutritionRepository)
        nutritionInfoViewModel = NutritionInfoViewModel(useCase)

        nutritionInfoViewModel.mealInfo.observe(this) {
            Log.d("meal", currentMeal.toString())
            if (it.food != null) {
                currentMeal = it
                binding.txtMealName.text = it.food.food_name
                binding.txtCalories.text = it.food.servings?.serving?.first()?.calories.toString()
            }

        }

        setListeners()

        foodItem?.let {
            nutritionInfoViewModel.findFoodInfo(it)
        }
    }

    private fun setListeners() {
        binding.btnAddMeal.setOnClickListener {
            if (currentMeal != null)
                nutritionInfoViewModel.addMeal(addMealDate, mealType, currentMeal!!.food)
            else
                TODO()

            val intent = Intent(this, ClientMenuActivity::class.java)
            intent.putExtra("fragment", "nutrition")
            startActivity(intent)

        }
    }
}