package com.example.flickerdemoapp.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.flickerdemoapp.R
import kotlinx.android.synthetic.main.item_photo_layout.view.*

class PhotoAdapter(private val mPhotoList: List<String>?, val context: Context) : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): PhotoAdapter.PhotoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_photo_layout, parent, false)
        return PhotoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mPhotoList!!.size
    }

    override fun onBindViewHolder(holder: PhotoAdapter.PhotoViewHolder, pos: Int) {
        Glide.with(context)
                .asBitmap()
                .load(mPhotoList?.get(pos))
                .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(holder.imgPhoto)
    }

    inner class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgPhoto = itemView.imgPhoto!!
    }

    companion object {
        private const val imageWidthPixels = 1024
        private const val imageHeightPixels = 768
    }

}