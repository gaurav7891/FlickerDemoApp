package com.example.flickerdemoapp.networking

import com.example.flickerdemoapp.module.FlickerPhotosEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("services/rest/?method=flickr.photos.getRecent&nojsoncallback=1&format=json")
    fun getRecent(
            @Query("api_key") apiKey: String
    ): Single<FlickerPhotosEntity>

    @GET("services/rest/?method=flickr.photos.search&nojsoncallback=1&format=json")
    fun search(
            @Query("api_key") apiKey: String,
            @Query("text") text: String? = null
    ): Single<FlickerPhotosEntity>


}