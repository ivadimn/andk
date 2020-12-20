package com.example.lists_2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun ViewGroup.inflate(@LayoutRes layoutRes : Int, attachToRoot : Boolean = false) : View {
    return LayoutInflater.from(this.context).inflate(layoutRes, this, attachToRoot)
}

fun AppCompatActivity.showFragment(@IdRes containerId : Int,
                fragment : Fragment, tag : String) : Unit {
    supportFragmentManager.beginTransaction()
        .replace(containerId, fragment, tag)
        .addToBackStack(tag)
        .commit()
}

fun <T : Fragment> T.withArguments(action : Bundle.() -> Unit) : T {
   return apply {
       val args = Bundle().apply(action)
       arguments = args
   }
}