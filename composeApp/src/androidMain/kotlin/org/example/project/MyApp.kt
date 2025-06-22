package org.example.project

import android.app.Application
import android.content.Context

lateinit var appContext: Context

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}
