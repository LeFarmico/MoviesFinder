package com.lefarmico.moviesfinder.utils.extension

import com.lefarmico.moviesfinder.R
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator

fun Picasso.loadWithThemeParams(path: String?): RequestCreator {
    return this
        .load(path)
        .fit()
        .error(R.drawable.ic_launcher_foreground)
        .placeholder(R.drawable.ic_launcher_foreground)
}
