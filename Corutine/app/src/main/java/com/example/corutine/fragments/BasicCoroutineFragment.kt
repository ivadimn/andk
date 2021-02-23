package com.example.corutine.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import kotlinx.coroutines.*
import java.math.BigInteger
import java.util.*
import kotlin.random.Random
import kotlin.random.asJavaRandom

class BasicCoroutineFragment : Fragment() {

    private val fragmentScope  = CoroutineScope(Dispatchers.Main)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        asyncExample()

    }

    private fun asyncExample() {
        val fragmentIOScope  = CoroutineScope(Dispatchers.IO)


        fragmentIOScope.launch {
            val start = System.currentTimeMillis()
            val deferredResult = fragmentIOScope.async {
                calculateNumber()
            }
            val deferredResult2 = fragmentIOScope.async {
                calculateNumber()
            }
            val result1 = deferredResult.await()
            val result2 = deferredResult2.await()
            val end = System.currentTimeMillis()
            Log.d("BasicCoroutineFragment", "sequential time  = ${end - start}")
        }

    }

    private fun calculateNumberExample() {
        fragmentScope.launch {
            Log.d("BasicCoroutineFragment", "launch inside from thread = ${Thread.currentThread().name}")
            calculateNumber()
            Log.d("BasicCoroutineFragment", "launch inside from thread = ${Thread.currentThread().name}")
        }
        Log.d("BasicCoroutineFragment", "coroutine launched")
    }

    private fun changeThreadExample() {
        fragmentScope.launch {
            (0 .. 200).forEach {
                Log.d("BasicCoroutineFragment", "start from thread = ${Thread.currentThread().name}")
                delay(100)
                Log.d("BasicCoroutineFragment", "end inside from thread = ${Thread.currentThread().name}")
            }
            Log.d("BasicCoroutineFragment", "coroutine launched")
        }
    }

    private suspend fun calculateNumber() : BigInteger {
        return withContext(Dispatchers.Default) {
            Log.d(
                "BasicCoroutineFragment",
                "calculate number from thread = ${Thread.currentThread().name}"
            )
            BigInteger.probablePrime(4000, Random.asJavaRandom())
        }
    }
}