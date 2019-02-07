package com.example.flickerdemoapp.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.flickerdemoapp.R
import com.example.flickerdemoapp.networking.NetworkError
import com.example.flickerdemoapp.utils.Constants
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var viewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        setObservers()
        init()
    }

    private fun setObservers() {
        viewModel?.errorMsg?.observe(this, Observer {
            onApiFailure(NetworkError(it).appErrorMessage)
        })
        viewModel?.showProgress?.observe(this, Observer {
            if (it!!) showLoading() else removeLoading()
        })
    }

    private fun onApiFailure(appErrorMessage: String) {
        Toast.makeText(this,appErrorMessage,Toast.LENGTH_SHORT).show()
    }

    private fun removeLoading() {
        progress.visibility = View.GONE
    }

    private fun showLoading() {
        progress.visibility = View.VISIBLE
    }

    private fun init(){
        btn.setOnClickListener {
            viewModel?.getRecentPhotos(Constants.API_KEY)
        }
    }
}
