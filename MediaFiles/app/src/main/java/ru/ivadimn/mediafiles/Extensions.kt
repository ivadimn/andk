package ru.ivadimn.mediafiles

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.toast(msg : String) {
    Toast.makeText(this.requireContext(), msg, Toast.LENGTH_SHORT).show()
}

fun <T: Fragment> T.withArguments(action: Bundle.() -> Unit) : T {
    return apply {
        val args = Bundle().apply(action)
        arguments = args
    }
}