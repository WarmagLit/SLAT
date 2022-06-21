package com.tsu.slat.data.entity

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MealInfo(
    val food: Food
) : Parcelable