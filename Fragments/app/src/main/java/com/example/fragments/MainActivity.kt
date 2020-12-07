package com.example.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var dataEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("LIFE_CYCLE", "Activity onCreate, ${hashCode()}")
        dataEditText = findViewById(R.id.dataEditText)
        findViewById<Button>(R.id.showFragmentButton).setOnClickListener {
            showInfoFragment()
        }
        findViewById<Button>(R.id.replaceFragmentButton).setOnClickListener {
            replaceInfoFragment()
        }
    }

    private fun showInfoFragment() {

        val alreadyHasFragment = supportFragmentManager.findFragmentById(R.id.container) != null
        //if (!alreadyHasFragment) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, InfoFragment.newInstance(dataEditText.text.toString()))
                .commit()
        //}
        //else {
         //   toast("Фрагмент уже добавлен")
        //}
    }

    private fun replaceInfoFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, InfoFragment())
            .commit()
    }

    private fun toast(msg : String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        Log.d("LIFE_CYCLE", "Activity onStart, ${hashCode()}")
    }

    override fun onResume() {
        super.onResume()
        Log.d("LIFE_CYCLE", "Activity onResume, ${hashCode()}")
    }

    override fun onPause() {
        super.onPause()
        Log.d("LIFE_CYCLE", "Activity onPause, ${hashCode()}")
    }

    override fun onStop() {
        super.onStop()
        Log.d("LIFE_CYCLE", "Activity onStop, ${hashCode()}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("LIFE_CYCLE", "Activity onDestroy, ${hashCode()}")
    }
}