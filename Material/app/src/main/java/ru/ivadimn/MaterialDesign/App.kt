package ru.ivadimn.MaterialDesign

import android.app.Application
import android.content.Context

class App : Application() {
    companion object {
        private var _context : Context? = null
        val context get() = ru.ivadimn.MaterialDesign.App.Companion._context!!
    }

    override fun onCreate() {
        super.onCreate()
        ru.ivadimn.MaterialDesign.App.Companion._context = applicationContext
    }
}