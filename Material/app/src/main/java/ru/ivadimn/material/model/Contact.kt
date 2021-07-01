package ru.ivadimn.material.model

import android.net.Uri

data class Contact(
    val id : Long,
    val uri : String,
    val name : String,
    val avatarUri : Uri?,
    var info : ContactInfo
)
