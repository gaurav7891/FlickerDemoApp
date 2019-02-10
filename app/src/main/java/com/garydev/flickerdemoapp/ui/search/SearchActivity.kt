package com.garydev.flickerdemoapp.ui.search

import android.app.SearchManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.Menu
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import com.garydev.flickerdemoapp.R
import com.garydev.flickerdemoapp.base.BaseActivity
import com.garydev.flickerdemoapp.networking.NetworkError
import com.garydev.flickerdemoapp.ui.MainViewModel
import com.garydev.flickerdemoapp.ui.PhotoAdapter
import com.garydev.flickerdemoapp.utils.Constants
import kotlinx.android.synthetic.main.activity_search.*


class SearchActivity : BaseActivity() {

    private var searchView: SearchView? = null
    private var viewModel: MainViewModel? = null
    private var photoAdapter: PhotoAdapter? = null
    private var pageCount: Int = 1
    private var searchText: String? = null
    private var listOfPhotos = ArrayList<String>()
    private var isScrolling: Boolean = false
    private var staggeredGridLayoutManager: StaggeredGridLayoutManager? = null
    private var currentItems: Int? = 0
    private var totalItems: Int? = 0
    private var scrollOutItems: Int = 0
    private var firstVisibleItems: IntArray? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.search_hint)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        setObservers()
        init()
        setListener()
    }


    private fun setListener() {
        search_recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                currentItems = staggeredGridLayoutManager?.childCount
                totalItems = staggeredGridLayoutManager?.itemCount
                firstVisibleItems = staggeredGridLayoutManager?.findFirstVisibleItemPositions(firstVisibleItems)

                if (firstVisibleItems?.isNotEmpty()!!) {
                    scrollOutItems = firstVisibleItems!![0]
                }

                if (isScrolling && (currentItems!!.plus(scrollOutItems) >= totalItems!!)) {
                    isScrolling = false
                    viewModel?.search(Constants.API_KEY, searchText!!, ++pageCount)
                }
            }
        })
    }

    private fun init() {
        staggeredGridLayoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        staggeredGridLayoutManager?.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        search_recycler_view?.setHasFixedSize(true)
        search_recycler_view?.layoutManager = staggeredGridLayoutManager
        photoAdapter = PhotoAdapter(listOfPhotos, this@SearchActivity)
        search_recycler_view.adapter = photoAdapter

        viewModel?.photoList?.observe(this, Observer {
            listOfPhotos.addAll(it!!)
            photoAdapter?.notifyItemInserted(listOfPhotos.size - 1)
        })
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val searchManger = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView?
        searchView?.setSearchableInfo(searchManger.getSearchableInfo(componentName))
        searchView?.maxWidth = Integer.MAX_VALUE

        // listening to search query text change
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // filter recycler view when query submitted
                searchText = query
                viewModel?.search(Constants.API_KEY, query, pageCount)
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                // filter recycler view when text is changed
                searchText = query
                return false
            }
        })
        return true
    }

}