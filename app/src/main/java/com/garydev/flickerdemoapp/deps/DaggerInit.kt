package com.garydev.flickerdemoapp.deps

import com.garydev.flickerdemoapp.FlickerApp
import com.garydev.flickerdemoapp.networking.NetworkModule

class DaggerInit{
    companion object {
        private lateinit var deps: Deps
        @JvmStatic
        fun initializeComponent(application: FlickerApp){
            deps = DaggerDeps.builder()
                    .networkModule(NetworkModule(application.cacheDir)).build()
        }

        @JvmStatic
        fun getDeps():Deps{
            return deps
        }
    }
}