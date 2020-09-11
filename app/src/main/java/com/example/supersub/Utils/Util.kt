package com.example.supersub.Utils

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.supersub.R

fun getProgressDrawable(context: Context):CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth=4f
        centerRadius=30f
        start()
    }
}

//we have added new method to load images in to imageview by using ktx
fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable){
    val options=RequestOptions().placeholder(progressDrawable).error(R.drawable.ic_image)
    Glide.with(context).setDefaultRequestOptions(options).load(uri).into(this)
}

@BindingAdapter("android:imageUrl")
fun loadimage(view: ImageView, uri: String?){
    view.loadImage(uri, getProgressDrawable(view.context))
}
