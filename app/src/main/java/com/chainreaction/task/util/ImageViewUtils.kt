package com.chainreaction.task.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chainreaction.task.movies.R

fun ImageView.loadImage(url: String) {
    Glide.with(this)
        .load(url)
        .placeholder(R.drawable.ic_place_holder)
        .into(this)
}