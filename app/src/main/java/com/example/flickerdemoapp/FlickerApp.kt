package com.example.flickerdemoapp

import android.app.Application
import com.example.flickerdemoapp.deps.DaggerInit

class FlickerApp:Application(){

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger() {
        DaggerInit.initializeComponent(this)
    }
}