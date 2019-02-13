package com.garydev.flickerdemoapp.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.*
import android.view.View
import android.widget.Toast
import com.garydev.flickerdemoapp.R
import com.garydev.flickerdemoapp.base.BaseActivity
import com.garydev.flickerdemoapp.networking.NetworkError
import com.garydev.flickerdemoapp.ui.search.SearchActivity
import com.garydev.flickerdemoapp.utils.Constants
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private var viewModel: MainViewModel? = null
    private var photoAdapter: PhotoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        title = getString(R.string.txt_photo_title)
        setObservers()
        init()
        setListener()
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
        Toast.makeText(this, appErrorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun removeLoading() {
        progress.visibility = View.GONE
    }

    private fun showLoading() {
        progress.visibility = View.VISIBLE
    }

    private fun init() {
        viewModel?.getRecentPhotos(Constants.API_KEY)
        viewModel?.photoList?.observe(this, Observer {
            photoAdapter = PhotoAdapter(it,this@MainActivity)
            val sgl = StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
            sgl.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
            recyclerView.setHasFixedSize(true)
            recyclerView?.layoutManager = sgl
            recyclerView.adapter = photoAdapter
        })
    }

    private fun setListener() {
        fabSearch.setOnClickListener {
            startActivity(Intent(this,SearchActivity::class.java))
        }
    }
}
