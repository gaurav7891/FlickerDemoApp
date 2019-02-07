package com.example.flickerdemoapp.deps

import com.example.flickerdemoapp.networking.NetworkModule
import com.example.flickerdemoapp.ui.MainViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface Deps {
    fun inject(viewModel: MainViewModel)
}