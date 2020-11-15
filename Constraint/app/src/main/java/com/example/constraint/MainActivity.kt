
package com.example.constraint

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toggLastNameBtn = findViewById<Button>(R.id.toggleLastName)
        toggLastNameBtn.setOnClickListener{
            lastName.isVisible = !lastName.isVisible
        }
    }
}