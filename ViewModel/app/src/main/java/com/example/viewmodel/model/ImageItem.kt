package com.example.viewmodel.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageItem(
    val id : Long,
    val link : String
) : Parcelable {
}