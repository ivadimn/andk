package ru.ivadimn.material

import android.app.Application
import android.content.Context

class App : Application() {
    companion object {
        private var _context : Context? = null
        val context get() = _context!!
    }

    override fun onCreate() {
        super.onCreate()
        _context = applicationContext
    }
}