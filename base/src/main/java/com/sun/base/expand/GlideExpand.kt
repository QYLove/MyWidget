package com.sun.base.expand

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

fun loadImage(
    context: Context,
    view: ImageView,
    url: String?,
    requestOptions: RequestOptions,
) {
    Glide.with(context)
        .load(url)
        .thumbnail(0.2f)
        .apply(requestOptions)
        .into(view)
}

fun clearMemory(context: Context) {
    Glide.get(context).clearMemory()
}

fun ImageView.loadRound(url: String?) {
    if (!url.isNullOrEmpty() && context != null) {
        val requestOptions =
            RequestOptions.circleCropTransform()
        loadImage(context, this, url, requestOptions)
    }
}

fun ImageView.loadRect(url: String?) {
    if (!url.isNullOrEmpty() && context != null) {
        val requestOptions =
            RequestOptions.centerCropTransform()
        loadImage(context, this, url, requestOptions)
    }
}

fun ImageView.loadRectFitCenter(url: String?) {
    if (!url.isNullOrEmpty() && context != null) {
        val requestOptions =
            RequestOptions.fitCenterTransform()
        loadImage(context, this, url, requestOptions)
    }
}

fun ImageView.loadRoundCorner(url: String?, roundedCorners: Int) {
    if (!url.isNullOrEmpty() && context != null) {
        val requestOptions =
            RequestOptions().transform(CenterCrop(), RoundedCorners(roundedCorners))
        loadImage(context, this, url, requestOptions)
    }
}

fun ImageView.loadRectFitCenter(image: ByteArray?) {
    if (context != null) {
        image?.let {
            val requestOptions =
                RequestOptions.fitCenterTransform()
            Glide.with(context)
                .load(it)
                .thumbnail(0.2f)
                .apply(requestOptions)
                .into(this)
        }
    }
}