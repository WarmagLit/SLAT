package com.tsu.slat.data.entity.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MealInfoResponse(
    val food: FoodResponse
) : Parcelable