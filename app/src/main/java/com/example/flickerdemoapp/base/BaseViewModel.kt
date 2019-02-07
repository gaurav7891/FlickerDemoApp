package com.example.flickerdemoapp.base

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel(app: Application) : AndroidViewModel(app) {

    var disposables: CompositeDisposable? = CompositeDisposable()

    var errorMsg = MutableLiveData<Throwable>()

    var showProgress = MutableLiveData<Boolean>()

    override fun onCleared() {
        super.onCleared()
        if (disposables != null) {
            disposables?.clear()
            disposables = null
        }
    }
}