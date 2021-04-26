package ru.ivadimn.mediafiles

import android.net.Uri

sealed class Media(
    open val id : Long,
    open val uri : Uri,
    open val name : String,
    open val size : Int
) {
    data class Image(
        override val id : Long,
        override val uri : Uri,
        override val name : String,
        override val size : Int
    ) : Media(id, uri, name, size) { }
}