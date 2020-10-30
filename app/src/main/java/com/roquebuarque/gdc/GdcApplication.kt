package com.roquebuarque.gdc

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

class GdcApplication : Application(){


    companion object{
        lateinit var instance: GdcApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        AndroidThreeTen.init(this)
    }
}