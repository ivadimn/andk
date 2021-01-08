package com.example.multithreading.fragments

import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.multithreading.R
import kotlin.random.Random

class HandlerFragment : Fragment(R.layout.fragment_handler) {

    private lateinit var handler: Handler
    private val mainHandler = Handler(Looper.getMainLooper())

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val backgroundThread = HandlerThread("BackgroundThread").apply {
            start()
        }

        handler = Handler(backgroundThread.looper)

        requireView().findViewById<Button>(R.id.executeButton)
                .setOnClickListener {
                    handler.post {
                        Log.d("Handler", "Execute task from thread - ${Thread.currentThread().name}")
                        val randomNumber = Random.nextLong()
                        mainHandler.post {
                            Log.d("Handler", "Execute task from thread - ${Thread.currentThread().name}")
                            requireView().findViewById<TextView>(R.id.executeTextView)
                                    .text = randomNumber.toString()
                        }
                        mainHandler.postDelayed({
                            Toast.makeText(requireContext(), "Generated number = ${randomNumber}", Toast.LENGTH_SHORT).show()
                        }, 1000)
                    }
                }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.looper.quit()
    }


}