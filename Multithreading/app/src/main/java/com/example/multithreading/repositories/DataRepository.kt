package com.example.multithreading.repositories

import com.example.multithreading.commons.Generator
import java.util.*
import java.util.concurrent.*

class DataRepository  {

    private val resultList = Collections.synchronizedList(mutableListOf<String>())

    fun getData( count : Int,  callback : (data: String, time: Long) -> Unit) {
        Thread {

            val threads = (0..count).chunked(10).map { numbers ->
                Thread {
                    val data = numbers.mapNotNull { num ->
                        generateString(num)
                    }
                    resultList.addAll(data)
                }
            }
            val startTime = System.currentTimeMillis()
            threads.forEach { it.start() }
            threads.forEach { it.join() }
            val interval = System.currentTimeMillis() - startTime
            callback(resultList.joinToString(separator = "\n"), interval)
        }.start()
    }


    private fun generateString(num : Int) : String {
        Thread.sleep(10)
        return "${num}. ${Generator.getNameCar()} ${Generator.getCountry()} ${Generator.getBirthday()}"
    }

    fun getDataEx(count : Int,  callback : (data: String, time: Long) -> Unit) {
        val nThreads = Runtime.getRuntime().availableProcessors() + 1
        val exec = Executors.newFixedThreadPool(6)
        Thread {
            val tasks = (0..count).chunked(count / nThreads).map { numbers ->
                Callable<List<String>>() {
                    numbers.mapNotNull { num ->
                        generateString(num)
                    }
                }
            }
            val startTime = System.currentTimeMillis()
            val futures = tasks.map { task -> exec.submit(task) }
            futures.forEach { future -> resultList.addAll(future.get()) }
            val interval = System.currentTimeMillis() - startTime
            callback(resultList.joinToString(separator = "\n"), interval)
        }.start()
    }

    fun getDataSingleThread(count : Int,  callback : (data: String, time: Long) -> Unit) {
        val startTime = System.currentTimeMillis()
        resultList.addAll((0 .. count).mapNotNull { num ->
            generateString(num)
        })
        val interval = System.currentTimeMillis() - startTime
        callback(resultList.joinToString(separator = "\n"), interval)
    }

}