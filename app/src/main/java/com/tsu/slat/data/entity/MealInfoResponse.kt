package com.tsu.slat.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MealInfoResponse(
    val food: FoodResponse
) : Parcelable