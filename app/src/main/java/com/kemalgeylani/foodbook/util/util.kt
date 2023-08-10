package com.kemalgeylani.foodbook.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kemalgeylani.foodbook.R

fun ImageView.downloadImage(url : String?, placeHolder : CircularProgressDrawable){

    val options = RequestOptions().placeholder(placeHolder).error(R.mipmap.ic_launcher)

    Glide.with(context).setDefaultRequestOptions(options).load(url).into(this)

}

fun makePlaceHolder(context : Context) : CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}