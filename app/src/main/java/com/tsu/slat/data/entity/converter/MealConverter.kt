package com.tsu.slat.data.entity.converter

import com.tsu.slat.data.entity.Food
import com.tsu.slat.data.entity.MealInfo
import com.tsu.slat.data.entity.MealInfoResponse
import com.tsu.slat.data.entity.ServingList

object MealConverter {
    fun responseToMealInfo(response: MealInfoResponse): MealInfo {
        val servings = response.food.servings?.serving?.toList()
        val servingList = servings?.let { ServingList(it) }
        val food = Food(response.food.food_id, response.food.food_name, response.food.food_type, response.food.food_url, servingList)
        return MealInfo(food)
    }
}