package com.garydev.flickerdemoapp.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.garydev.flickerdemoapp.R
import com.garydev.flickerdemoapp.utils.GlideApp
import kotlinx.android.synthetic.main.item_photo_layout.view.*

class PhotoAdapter(private val mPhotoList: List<String>?, val context: Context) : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    private val isDownloaded: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): PhotoAdapter.PhotoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_photo_layout, parent, false)
        return PhotoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mPhotoList!!.size
    }

    override fun onBindViewHolder(holder: PhotoAdapter.PhotoViewHolder, pos: Int) {
        val item = mPhotoList?.get(pos)
        downloadImage(item,holder.imgPhoto)
    }

    private fun downloadImage(item: String?, imgPhoto: ImageView) {
        GlideApp.with(context)
                .asBitmap()
                .load(item)
                .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(imgPhoto)
    }

    inner class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgPhoto = itemView.imgPhoto!!
    }

}