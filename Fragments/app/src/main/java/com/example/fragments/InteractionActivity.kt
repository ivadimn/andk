package com.example.fragments

import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class InteractionActivity : AppCompatActivity(R.layout.activity_interaction) , ItemSelectListener {
    override fun onItemSelected(text: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, InfoFragment.newInstance(text))
            .commit()
    }

}