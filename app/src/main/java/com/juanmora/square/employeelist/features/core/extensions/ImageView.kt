package com.juanmora.square.employeelist.features.core.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.juanmora.square.employeelist.R

private const val CROSS_FADE_DURATION = 300

sealed class CornerType {
    object None : CornerType()
    object Circle : CornerType()
}

fun ImageView.download(
    item: String?,
    cornerType: CornerType = CornerType.None
) {
    if (item == null) return
    val requestOptions = when (cornerType) {
        is CornerType.None -> RequestOptions()
        is CornerType.Circle -> RequestOptions.circleCropTransform()
    }.placeholder(R.drawable.img_placeholder)
        .error(R.drawable.img_placeholder)
        .diskCacheStrategy(DiskCacheStrategy.ALL)

    Glide.with(context)
        .load(item)
        .transition(DrawableTransitionOptions.withCrossFade(CROSS_FADE_DURATION))
        .apply(requestOptions)
        .into(this)
}
