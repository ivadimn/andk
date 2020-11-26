package com.example.states

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val TAG  = "MainActivity"
    private lateinit var textView: TextView
    private var state : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DebugLogger.d(TAG, "onCreate was called ${hashCode()}")
        //error("test crash")
        textView = findViewById<TextView>(R.id.textView)
        setCounterValue()
        findViewById<Button>(R.id.buttonIncrease).setOnClickListener {
            state++
            setCounterValue()
        }

        findViewById<Button>(R.id.buttonDecrease).setOnClickListener {
            state--
            setCounterValue()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_COUNTER, state)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        state = savedInstanceState.getInt(KEY_COUNTER)
        setCounterValue()
    }

    private fun setCounterValue() {
        textView.text = state.toString()
    }

    override fun onStart() {
        super.onStart()
        DebugLogger.d(TAG, "onStart was called ${hashCode()}")
    }

    override fun onResume() {
        super.onResume()
        DebugLogger.d(TAG, "onResume was called ${hashCode()}")
    }

    override fun onPause() {
        super.onPause()
        DebugLogger.d(TAG, "onPause was called ${hashCode()}")
    }

    override fun onStop() {
        super.onStop()
        DebugLogger.d(TAG, "onStop was called ${hashCode()}")
    }

    override fun onDestroy() {
        super.onDestroy()
        DebugLogger.d(TAG, "onDestroy was called ${hashCode()}")
    }

    override fun onTopResumedActivityChanged(isTopResumedActivity: Boolean) {
        super.onTopResumedActivityChanged(isTopResumedActivity)
        DebugLogger.d(TAG, "onTopResumedActivityChanged was called ${hashCode()} $isTopResumedActivity")
    }

    companion object {
        private val KEY_COUNTER = "counterState"
    }
}

object DebugLogger {
    fun d(tag : String, msg : String) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, msg)
        }
    }
}