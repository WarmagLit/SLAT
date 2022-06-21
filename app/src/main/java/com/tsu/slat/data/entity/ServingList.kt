package com.tsu.slat.data.entity

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ServingList(
    val serving: List<Serving> = listOf()
) : Parcelable