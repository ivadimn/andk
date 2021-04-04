package com.example.db

import android.app.Application
import android.content.Context
import com.example.db.database.Db

class App : Application() {

    companion object {
        private var _context : Context? = null
        val context get() = _context!!
    }

    override fun onCreate() {
        super.onCreate()
        _context = applicationContext
        Db.init(this)
    }
}