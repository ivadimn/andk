package ru.ivadimn.ipdf

import android.app.Application
import android.content.Context

class App : Application() {
    companion object {
        private var _context : Context? = null
        val context get() = _context!!
        const val MEDIA_EXTERNAL_DIR = "photos_external_dir"
    }

    override fun onCreate() {
        super.onCreate()
        _context = applicationContext
    }
}