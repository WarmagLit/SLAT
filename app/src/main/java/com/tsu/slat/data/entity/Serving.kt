package com.tsu.slat.data.entity

data class Serving(
    val serving_id: Int,
    val serving_description: String,
    val metric_serving_amount: Double,
    val metric_serving_unit: String,
    val number_of_units: Double,
    val measurement_description: String,

    val calories: Double,
    val carbohydrate: Double,
    val protein: Double,
    val fat: Double
)


