package ru.ivadimn.ivnplayer

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.toast(msg : String) {
    Toast.makeText(this.requireContext(), msg, Toast.LENGTH_LONG).show()
}

fun haveQ() : Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
fun haveR() : Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.R
fun haveO() : Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O