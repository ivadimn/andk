package ru.ivadimn.notifications

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.toast(msg : String) {
    Toast.makeText(this.requireContext(), msg, Toast.LENGTH_LONG).show()
}

fun <T: Fragment> T.withArguments(action: Bundle.() -> Unit) : T {
    return apply {
        val args = Bundle().apply(action)
        arguments = args
    }
}

fun haveQ() : Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
fun haveR() : Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.R
fun haveO() : Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O