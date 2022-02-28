package ru.ivadimn.timer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Chronometer

class MainActivity : AppCompatActivity() {

    private lateinit var chronometer: Chronometer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



    }
}