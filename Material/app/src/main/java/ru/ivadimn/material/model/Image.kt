package ru.ivadimn.material.model

import android.net.Uri

data class Image(
    val id : Long,
    val uri : Uri,
    val name : String,
    val description : String,
    val size : Long,
    val width : Int,
    val height : Int
)
