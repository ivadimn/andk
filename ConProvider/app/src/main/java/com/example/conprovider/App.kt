package com.example.conprovider

import android.app.Application
import android.content.Context

class App : Application() {

    companion object {
        private lateinit var _context : Context
        val context get() = _context!!
    }

    override fun onCreate() {
        super.onCreate()
        _context = applicationContext
    }
}