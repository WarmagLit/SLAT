package com.tsu.slat.data.entity.response

import com.tsu.slat.data.entity.FoodItemShort

data class FindFoodResponse(
    val food : List<FoodItemShort>,
    val max_results : Int,
    val page_number : Int,
    val total_results : Int
)





