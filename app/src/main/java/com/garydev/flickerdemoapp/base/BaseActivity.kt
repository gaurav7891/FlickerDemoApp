package com.garydev.flickerdemoapp.base

import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.garydev.flickerdemoapp.R

open class BaseActivity:AppCompatActivity(){

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.action_search ->{return true}

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

}