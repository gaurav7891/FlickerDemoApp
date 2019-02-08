package com.example.flickerdemoapp.ui.search

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import com.example.flickerdemoapp.R
import kotlinx.android.synthetic.main.activity_search.*


class SearchActivity : AppCompatActivity() {

    private var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.search_hint)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val searchManger = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView?
        searchView?.setSearchableInfo(searchManger.getSearchableInfo(componentName))
        searchView?.maxWidth = Integer.MAX_VALUE

        /* // listening to search query text change
         searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
             override fun onQueryTextSubmit(query: String): Boolean {
                 // filter recycler view when query submitted
                 mAdapter.getFilter().filter(query)
                 return false
             }

             override fun onQueryTextChange(query: String): Boolean {
                 // filter recycler view when text is changed
                 mAdapter.getFilter().filter(query)
                 return false
             }
         })
         return true*/
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        val id = item?.itemId
        if (id == R.id.action_search) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (!searchView?.isIconified!!) {
            searchView?.isIconified = true
            return
        }
        super.onBackPressed()
    }
}