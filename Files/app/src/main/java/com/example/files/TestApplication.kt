package com.example.files

import android.app.Application
import android.content.Context
import android.os.StrictMode

class TestApplication : Application() {

    companion object {
        private lateinit var  _context : Context
        val context
        get() = _context

        private lateinit var _instance : Application
        val instance
        get() = _instance
    }


    override fun onCreate() {
        super.onCreate()

        TestApplication._context = applicationContext
        TestApplication._instance = this

        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()
                    .penaltyDeath()
                    .build()
            )
        }

    }


}