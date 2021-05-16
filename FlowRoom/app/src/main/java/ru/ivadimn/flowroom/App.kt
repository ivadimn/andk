package ru.ivadimn.flowroom

import android.app.Application
import ru.ivadimn.flowroom.db.Db

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Db.init(applicationContext)
    }
}