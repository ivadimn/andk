package ru.ivadimn.notifications

import android.app.Application
import ru.ivadimn.notifications.presentation.NotificationChannels

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        NotificationChannels.create(this)
    }
}