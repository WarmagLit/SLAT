package com.tsu.slat.data.entity

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Serving(
    val serving_id: Int = 0,
    val serving_description: String = "",
    val metric_serving_amount: Double= 0.0,
    val metric_serving_unit: String= "",
    val number_of_units: Double= 0.0,
    val measurement_description: String = "",

    val calories: Double = 0.0,
    val carbohydrate: Double= 0.0,
    val protein: Double= 0.0,
    val fat: Double= 0.0
) : Parcelable

