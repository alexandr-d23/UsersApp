package com.example.userlist

import android.app.Application
import com.example.userlist.di.components.AppComponent
import com.example.userlist.di.components.DaggerAppComponent

class ApplicationDelegate : Application() {

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}