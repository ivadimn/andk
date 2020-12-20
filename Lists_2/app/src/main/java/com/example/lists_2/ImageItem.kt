package com.example.lists_2

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageItem(
    val id : Long,
    val link : String
) : Parcelable {
}