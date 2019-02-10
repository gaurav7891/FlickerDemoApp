package com.garydev.flickerdemoapp.deps

import com.garydev.flickerdemoapp.networking.NetworkModule
import com.garydev.flickerdemoapp.ui.MainViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface Deps {
    fun inject(viewModel: MainViewModel)
}