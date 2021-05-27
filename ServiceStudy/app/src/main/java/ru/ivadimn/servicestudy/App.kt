package ru.ivadimn.servicestudy

import android.app.Application
import ru.ivadimn.servicestudy.services.NotificationChannels

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        NotificationChannels.create(applicationContext)
    }
}