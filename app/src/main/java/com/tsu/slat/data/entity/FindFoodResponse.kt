package com.tsu.slat.data.entity

data class FindFoodResponse(
    val food : List<FoodItemShort>,
    val max_results : Int,
    val page_number : Int,
    val total_results : Int
)





