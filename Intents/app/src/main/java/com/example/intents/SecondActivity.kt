package com.example.intents

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_second.*
import java.security.AccessControlContext

class SecondActivity : AppCompatActivity(R.layout.activity_second) {
    companion object {
        private val MESSAGE_KEY = "MESSAGE_KEY"
        fun getIntent(context: Context, message : String) : Intent =
            Intent(context, SecondActivity::class.java)
                .putExtra(MESSAGE_KEY, message)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("LifecycleTest", "SecondActivity|onCreate|${hashCode()}")
        val message = intent.getStringExtra(MESSAGE_KEY)
        messageTextView.text = message
    }

    override fun onStart() {
        super.onStart()
        Log.d("LifecycleTest", "SecondActivity|onStart|${hashCode()}")
    }

    override fun onResume() {
        super.onResume()
        Log.d("LifecycleTest", "SecondActivity|onResume|${hashCode()}")
    }

    override fun onPause() {
        super.onPause()
        Log.d("LifecycleTest", "SecondActivity|onPause|${hashCode()}")
    }

    override fun onStop() {
        super.onStop()
        Log.d("LifecycleTest", "SecondActivity|onStop|${hashCode()}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("LifecycleTest", "SecondActivity|onDestroy|${hashCode()}")
    }
}