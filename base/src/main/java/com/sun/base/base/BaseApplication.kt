package com.sun.base.base

import android.app.Application
import android.content.Context

class BaseApplication:Application() {

    companion object{
        lateinit var context:Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}