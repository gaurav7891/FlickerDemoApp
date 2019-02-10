package com.garydev.flickerdemoapp

import android.app.Application
import com.garydev.flickerdemoapp.deps.DaggerInit

class FlickerApp:Application(){

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger() {
        DaggerInit.initializeComponent(this)
    }
}