package com.tsu.slat.presentation.screens.client_menu.ui.nutrition.day_meal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.tsu.slat.R
import com.tsu.slat.data.entity.DayMeal
import com.tsu.slat.data.entity.Food
import com.tsu.slat.data.entity.FoodItemShort
import com.tsu.slat.data.entity.Mealtime
import com.tsu.slat.databinding.FoodInfoLayoutBinding
import com.tsu.slat.databinding.FragmentDayMealBinding
import com.tsu.slat.presentation.screens.find_food.FindFoodActivity
import com.tsu.slat.presentation.screens.nutrition_info.NutritionInfoActivity
import java.time.LocalDate


class DayMealFragment(day: LocalDate) : Fragment() {

    private var _binding: FragmentDayMealBinding? = null

    private val binding get() = _binding!!

    private val viewModel by viewModels<DayMealViewModel>()

    private val today = day

    constructor() : this(LocalDate.now() /* other properties default values */)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDayMealBinding.inflate(inflater, container, false)

        initMealContainers()
        initObservers()

        viewModel.dayMeals.observe(viewLifecycleOwner) {
            Log.d("Meal", "updated")
            updateDayMeals(inflater, container, it)
        }


        return binding.root
    }

    private fun initObservers() {
        viewModel.dayCalories.observe(viewLifecycleOwner) {
            binding.include.txtCaloriesGoal.text = "2000"
            binding.include.txtCaloriesEaten.text = it.toString()
            binding.include.txtCaloriesLeft.text = (2000 - it).toString()
        }
    }

    private fun initMealContainers() {
        binding.breakfast.txtAddMeal.isClickable = true
        binding.breakfast.txtAddMeal.setOnClickListener {
            //val food = FoodInfoLayoutBinding.inflate(inflater, container, false).root
            //binding.breakfast.foodList.addView(food)

            val currentDate = today
            val intent = Intent(requireContext(), FindFoodActivity::class.java)
            intent.putExtra("mealtime", Mealtime.BREAKFAST)
            intent.putExtra("date", currentDate)
            startActivity(intent)
        }

        binding.lunch.txtAddMeal.isClickable = true
        binding.lunch.txtMealType.text = getText(R.string.lunch)
        binding.lunch.txtAddMeal.setOnClickListener {
            val currentTime = today
            val intent = Intent(requireContext(), FindFoodActivity::class.java)
            intent.putExtra("mealtime", Mealtime.LUNCH)
            intent.putExtra("date", currentTime)
            startActivity(intent)
        }

        binding.dinner.txtAddMeal.isClickable = true
        binding.dinner.txtMealType.text = getText(R.string.dinner)
        binding.dinner.txtAddMeal.setOnClickListener {
            val currentTime = today
            val intent = Intent(requireContext(), FindFoodActivity::class.java)
            intent.putExtra("mealtime", Mealtime.DINNER)
            intent.putExtra("date", currentTime)
            startActivity(intent)
        }

        binding.snack.txtAddMeal.isClickable = true
        binding.snack.txtMealType.text = getText(R.string.snack)
        binding.snack.txtAddMeal.setOnClickListener {
            val currentTime = today
            val intent = Intent(requireContext(), FindFoodActivity::class.java)
            intent.putExtra("mealtime", Mealtime.SNACK)
            intent.putExtra("date", currentTime)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateDayMeals(inflater: LayoutInflater, container: ViewGroup?,dayMeals: List<DayMeal>) {
        clearViews()
        viewModel.dayCalories.value = 0
        for (meal in dayMeals) {
            if (LocalDate.parse(meal.date) == today) {
                addMeal(inflater, container, meal)
            }
        }
    }

    private fun clearViews() {
        binding.breakfast.foodList.removeAllViews()
        binding.lunch.foodList.removeAllViews()
        binding.dinner.foodList.removeAllViews()
        binding.snack.foodList.removeAllViews()
    }

    private fun addMeal(inflater: LayoutInflater, container: ViewGroup?, dayMeal: DayMeal) {
        viewModel.dayCalories.value = viewModel.dayCalories.value?.plus(dayMeal.food!!.servings?.serving?.get(0)?.calories!!.toInt())

        val food_binding = FoodInfoLayoutBinding.inflate(inflater, container, false)
        val food = food_binding.root
        food_binding.layoutFoodInfo.setOnClickListener {
            if (dayMeal.food != null) {
                val intent = Intent(requireContext(), NutritionInfoActivity::class.java)
                intent.putExtra("FOOD_ITEM_SHORT", foodToFoodItemShort(dayMeal.food))
                intent.putExtra("mealtime", dayMeal.type)
                intent.putExtra("date", LocalDate.parse(dayMeal.date))
                startActivity(intent)
            }
        }

        when(dayMeal.type) {
            Mealtime.BREAKFAST -> binding.breakfast.foodList.addView(food)
            Mealtime.LUNCH -> binding.lunch.foodList.addView(food)
            Mealtime.DINNER -> binding.dinner.foodList.addView(food)
            Mealtime.SNACK -> binding.snack.foodList.addView(food)
        }

        if (dayMeal.food != null) {
            food_binding.txtMealName.text = dayMeal.food.food_name
            food_binding.txtMealBrand.text = dayMeal.food.servings?.serving?.get(0)?.calories.toString()
        } else {
            food_binding.txtMealName.text = "Unknown"
            food_binding.txtMealBrand.text = "Unknown brand"
        }
    }

    private fun foodToFoodItemShort(food: Food): FoodItemShort {
        return FoodItemShort(food.food_id, food.food_name, food.food_type, food.food_url, "Description")
    }
}