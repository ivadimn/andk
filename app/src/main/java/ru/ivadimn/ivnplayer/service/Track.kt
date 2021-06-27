package ru.ivadimn.ivnplayer.service

import android.net.Uri

data class Track(
    val id : Long,
    val name : String,
    val title : String,
    val size : Long,
    val uri : Uri,
    val artist : String,
    val duration : Long,
    val album: String
) {
}