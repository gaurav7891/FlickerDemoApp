package com.garydev.flickerdemoapp.ui

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.garydev.flickerdemoapp.base.BaseViewModel
import com.garydev.flickerdemoapp.deps.DaggerInit
import com.garydev.flickerdemoapp.model.FlickerPhotosEntity
import com.garydev.flickerdemoapp.model.Photo
import com.garydev.flickerdemoapp.networking.NetworkServiceImpl
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel(application: Application) : BaseViewModel(application) {
    @Inject
    lateinit var service: NetworkServiceImpl

    var photoList = MutableLiveData<List<String>>()

    init {
        DaggerInit.getDeps().inject(this)
    }

    fun getRecentPhotos(apiKey: String) {
        service.getRecent(apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<FlickerPhotosEntity> {
                    override fun onSuccess(t: FlickerPhotosEntity) {
                        showProgress.value = false
                        photoList.value = createImageLink(t.photos?.photo)

                    }

                    override fun onSubscribe(d: Disposable) {
                        disposables?.add(d)
                        showProgress.value = true
                    }

                    override fun onError(e: Throwable) {
                        errorMsg.value = e
                        showProgress.value = false
                    }

                })
    }

    fun search(apiKey: String, text: String,page:Int) {
        service.search(apiKey, text,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<FlickerPhotosEntity> {
                    override fun onSuccess(t: FlickerPhotosEntity) {
                        showProgress.value = false
                        photoList.value = createImageLink(t.photos?.photo)
                    }

                    override fun onSubscribe(d: Disposable) {
                        disposables?.add(d)
                        showProgress.value = true
                    }

                    override fun onError(e: Throwable) {
                        errorMsg.value = e
                        showProgress.value = false
                    }
                })
    }

    fun createImageLink(photo: List<Photo>?): ArrayList<String> {
        val list = ArrayList<String>()
        for (p in photo!!) {
            val photoLink = "https://farm${p.farm}.staticflickr.com/${p.server}/${p.id}_${p.secret}.jpg"
            list.add(photoLink)
        }
        for (x in list) {
            println(x)
        }

        return list
    }


}