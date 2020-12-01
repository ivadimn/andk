package com.example.intents

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class EmailActivity : AppCompatActivity(R.layout.activity_email) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setEmailParamsFromIntent()
    }

    private fun setEmailParamsFromIntent() {
        val addresses  = intent.getStringArrayExtra(Intent.EXTRA_EMAIL)
        val subject = intent.getStringExtra(Intent.EXTRA_SUBJECT)

        findViewById<TextView>(R.id.emailAddressTextView).text =
                              addresses?.joinToString() ?: "Email address is not set"
        findViewById<TextView>(R.id.subjectTextView).text = subject ?: "Subject is not set"

    }
}