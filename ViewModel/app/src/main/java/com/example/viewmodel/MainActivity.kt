package com.example.viewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.viewmodel.fragments.EntityListFragment
import com.example.viewmodel.fragments.ImageListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun showEntityListFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.mainContainer, EntityListFragment())
            .commit()
    }

    private fun showImageListFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.mainContainer, ImageListFragment())
            .commit()
    }

}