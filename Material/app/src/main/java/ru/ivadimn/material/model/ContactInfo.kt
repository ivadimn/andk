package ru.ivadimn.material.model

import android.net.Uri
import android.os.Parcelable

data class ContactInfo(
    var photoUri : Uri?,
    var details : List<ItemDetail> = emptyList()
) {
}