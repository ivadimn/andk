package com.example.intents

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CourseActivity : AppCompatActivity(R.layout.activity_course) {

    private lateinit var courseNameTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        courseNameTextView = findViewById(R.id.courseNameTextView)

        handleIntentData()
    }

    //https://skillbox.ru/course/profession-android-developer/

    private fun handleIntentData() {

        intent.data?.lastPathSegment?.let { courseName ->
            courseNameTextView.text = courseName
        }
    }

}