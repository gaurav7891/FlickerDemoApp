package com.garydev.flickerdemoapp.networking

import com.garydev.flickerdemoapp.model.FlickerPhotosEntity
import io.reactivex.Single

class NetworkServiceImpl(private val mNetworkService: NetworkService) : NetworkService {

    override fun getRecent(apiKey: String): Single<FlickerPhotosEntity> {
        return mNetworkService.getRecent(apiKey)
    }

    override fun search(apiKey: String, text: String?, page: Int?): Single<FlickerPhotosEntity> {
        return mNetworkService.search(apiKey, text,page)
    }

}