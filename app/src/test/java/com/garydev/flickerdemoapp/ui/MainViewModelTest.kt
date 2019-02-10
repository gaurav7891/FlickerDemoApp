package com.garydev.flickerdemoapp.ui

import com.garydev.flickerdemoapp.model.FlickerPhotosEntity
import com.garydev.flickerdemoapp.networking.NetworkServiceImpl
import io.reactivex.Single
import org.junit.Test

import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    lateinit var viewModel:MainViewModel

    @Mock
    private val service: NetworkServiceImpl? = null

    @Mock
    lateinit var observer : Single<FlickerPhotosEntity>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testSearchPhotoWithSearchKey(){
        Mockito.`when`(service?.search("","",1)).thenReturn(observer)
    }
}

