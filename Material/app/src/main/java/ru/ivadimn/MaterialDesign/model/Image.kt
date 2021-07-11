package ru.ivadimn.MaterialDesign.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Image(
    val id : Long,
    val uri : Uri,
    val name : String,
    val description : String,
    val longDescription : String,
    val size : Long,
    val width : Int,
    val height : Int
) : Parcelable {

}
