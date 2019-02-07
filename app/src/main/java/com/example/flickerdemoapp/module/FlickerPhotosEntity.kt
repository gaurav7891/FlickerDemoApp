package com.example.flickerdemoapp.module

/**
 * Represents response of the api call
 */
data class FlickerPhotosEntity(var photos: FlickerPhoto? = null,
                               var stat: String? = null)

data class FlickerPhoto(
        var page: Int? = null,
        var pages: Int? = null,
        var perpage: Int? = null,
        var total: Int? = null,
        var photo: List<Photo>? = null
)

data class Photo(
        var id: String? = null,
        var owner: String? = null,
        var secret: String? = null,
        var server: String? = null,
        var farm: Int? = null,
        var title: String? = null,
        var ispublic: Int? = null,
        var isfriend: Int? = null,
        var isfamily: Int? = null
)