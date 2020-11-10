package com.example.skillbox

import android.graphics.Color
import android.os.Bundle
import android.text.Layout
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_dynamic.*
import kotlinx.android.synthetic.main.activity_dynamic.container
import kotlinx.android.synthetic.main.activity_dynamic.view.*
import kotlinx.android.synthetic.main.item_text.view.*
import kotlin.random.Random

class DynamicActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynamic)

        val parent = container

        addButton.setOnClickListener{
            val textToAdd = textinput.text.toString()
            val view = layoutInflater.inflate(R.layout.item_text, parent, false)
            view.apply {
                textView.text = textToAdd
                deleteButton.setOnClickListener {
                    parent.removeView(this)
                }
            }
            parent.addView(view)
        }
    }

    private fun generateView(textView : String) =  TextView(this).apply {
        text = textView
        layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            gravity = when(Random.nextInt(3)) {
                0 -> Gravity.CENTER
                1 -> Gravity.END
                else -> Gravity.START
            }
        }
    }


}