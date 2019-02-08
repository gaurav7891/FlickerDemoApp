package com.example.flickerdemoapp.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.provider.Contacts
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import android.widget.Toast
import com.example.flickerdemoapp.R
import com.example.flickerdemoapp.networking.NetworkError
import com.example.flickerdemoapp.ui.search.SearchActivity
import com.example.flickerdemoapp.utils.Constants
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var viewModel: MainViewModel? = null
    private var photoAdapter: PhotoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        title = getString(R.string.txt_photo_title)
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
            val sgl = StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
            sgl.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
            recyclerView?.setHasFixedSize(true)
            recyclerView?.layoutManager = sgl
            recyclerView.adapter = photoAdapter
        })

        fabSearch.setOnClickListener {
            startActivity(Intent(this,SearchActivity::class.java))
        }
    }
}
