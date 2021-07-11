package ru.ivadimn.di

import android.app.Application
import android.content.Context
import com.jakewharton.threetenabp.AndroidThreeTen
import ru.ivadimn.di.data.database.Db


class App : Application() {
    companion object {
        private var _context : Context? = null
        val context get() = _context!!
        const val MEDIA_EXTERNAL_DIR = "photos_external_dir"
    }

    override fun onCreate() {
        super.onCreate()
        _context = applicationContext
        AndroidThreeTen.init(this)
        Db.init(applicationContext)
    }
}