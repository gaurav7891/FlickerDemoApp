package com.example.flickerdemoapp.deps

import com.example.flickerdemoapp.FlickerApp
import com.example.flickerdemoapp.networking.NetworkModule

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