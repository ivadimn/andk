package com.example.workstudy

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.toast(msg : String) {
    Toast.makeText(this.requireContext(), msg, Toast.LENGTH_LONG).show()
}