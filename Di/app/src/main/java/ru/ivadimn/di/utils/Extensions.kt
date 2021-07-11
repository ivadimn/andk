package ru.ivadimn.di

import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.toast(msg : String) {
    Toast.makeText(this.requireContext(), msg, Toast.LENGTH_LONG).show()
}


fun haveQ() : Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
fun haveR() : Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.R