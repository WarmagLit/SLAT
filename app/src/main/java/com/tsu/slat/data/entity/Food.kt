package com.tsu.slat.data.entity

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Food(
    val food_id : Int = 0,
    val food_name : String = "",
    val food_type : String = "",
    val food_url : String = "",
    val servings: ServingList? = null
) : Parcelable


