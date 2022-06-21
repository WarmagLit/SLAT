package com.tsu.slat.presentation.screens.find_food

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tsu.slat.data.entity.Food
import com.tsu.slat.data.entity.FoodItemShort
import com.tsu.slat.domain.usecases.FindFoodsUseCase
import kotlinx.coroutines.launch

class FindFoodViewModel(private val useCase: FindFoodsUseCase) : ViewModel() {


    var selectedFood = MutableLiveData<FoodItemShort>()

    var foods: List<FoodItemShort>

    init {
        foods = listOf()
    }

    val adapter: FindFoodAdapter = FindFoodAdapter(this)

    fun getFoods(name: String) {
        viewModelScope.launch {
            val res = useCase.findFood(name)
            if (res != null) {
                foods = res.foods.food
                updateRecycler()
            }
            Log.d("resss",res.toString())
        }
    }

    fun getDetailedInfo(food: FoodItemShort) {
        selectedFood.value = food
        Log.d("get", "info")
    }

    fun updateRecycler(){
        adapter.foodList.clear()
        val foodList = foods

        if (foodList.isEmpty()) {
            adapter.clearList()
        }
        for (food in foodList) {
            adapter.addFood(food)
        }
    }
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
}