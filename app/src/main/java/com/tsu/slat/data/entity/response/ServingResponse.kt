package com.tsu.slat.data.entity.response

import android.os.Parcelable
import com.tsu.slat.data.entity.Serving
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ServingResponse(
    val serving: Array<Serving> = arrayOf()
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ServingResponse

        if (!serving.contentEquals(other.serving)) return false

        return true
    }

    override fun hashCode(): Int {
        return serving.contentHashCode()
    }
}