package com.garydev.flickerdemoapp.networking

import com.garydev.flickerdemoapp.model.FlickerPhotosEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("services/rest/?method=flickr.photos.getRecent&nojsoncallback=1&format=json&safe_search=1")
    fun getRecent(
            @Query("api_key") apiKey: String
    ): Single<FlickerPhotosEntity>

    @GET("services/rest/?method=flickr.photos.search&nojsoncallback=1&format=json&safe_search=1")
    fun search(
            @Query("api_key") apiKey: String,
            @Query("text") text: String? = null,
            @Query("page") page: Int?=null
    ): Single<FlickerPhotosEntity>


}