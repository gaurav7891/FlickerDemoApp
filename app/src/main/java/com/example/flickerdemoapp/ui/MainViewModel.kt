package com.example.flickerdemoapp.ui

import android.app.Application
import com.example.flickerdemoapp.base.BaseViewModel
import com.example.flickerdemoapp.deps.DaggerInit
import com.example.flickerdemoapp.module.FlickerPhotosEntity
import com.example.flickerdemoapp.networking.NetworkServiceImpl
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel(application: Application) : BaseViewModel(application) {
    @Inject
    lateinit var service: NetworkServiceImpl

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

    fun search(apiKey: String, text: String) {
        service.search(apiKey, text)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<FlickerPhotosEntity> {
                    override fun onSuccess(t: FlickerPhotosEntity) {
                        showProgress.value = false
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
}