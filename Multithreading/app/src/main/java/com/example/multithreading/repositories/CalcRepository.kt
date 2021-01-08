package com.example.multithreading.repositories

import java.util.concurrent.atomic.AtomicLong

class CalcRepository {

    private var number = AtomicLong(0)
    private val nThreads = 100

    fun calculate(countIterations : Int = 1000000, callback : (value : Long) -> Unit) {
        number.set(0)
        (0 until nThreads).map { n ->
            Thread {
                for (i in 0 until countIterations) {
                   number.incrementAndGet()
                }
            }.apply {
                start()
            }
        }.map { it.join() }
        callback(number.get())
    }
}