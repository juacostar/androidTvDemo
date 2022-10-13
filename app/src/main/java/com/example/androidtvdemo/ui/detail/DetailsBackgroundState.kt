package com.example.androidtvdemo.ui.detail

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.bumptech.glide.request.transition.Transition
import androidx.leanback.app.DetailsSupportFragment
import androidx.leanback.app.DetailsSupportFragmentBackgroundController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget

class DetailsBackgroundState(private val detailFragment: DetailsSupportFragment) {
    private val detailsBackground by lazy { DetailsSupportFragmentBackgroundController(detailFragment) }
    private val target = object : CustomTarget<Bitmap>() {
        override fun onResourceReady(
            bitmap: Bitmap,
            transition: Transition<in Bitmap>?
        ) {
            detailsBackground.coverBitmap = bitmap
            detailsBackground.enableParallax()
        }

        override fun onLoadCleared(placeholder: Drawable?) {}
    }

    fun loadUrl(url: String) {
        Glide.with(detailFragment.requireContext())
            .asBitmap()
            .centerCrop()
            .load(url)
            .into<CustomTarget<Bitmap>>(target)
    }
}
