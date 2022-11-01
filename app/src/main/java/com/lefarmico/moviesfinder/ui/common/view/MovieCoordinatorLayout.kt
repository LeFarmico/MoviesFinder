package com.lefarmico.moviesfinder.ui.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.annotation.Nullable
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.databinding.BottomSheetMovieRecycleBinding
import com.lefarmico.moviesfinder.private.Private
import com.squareup.picasso.Picasso

class MovieCoordinatorLayout(
    context: Context,
    @Nullable attributeSet: AttributeSet
) : CoordinatorLayout(context, attributeSet) {

    private var binding: BottomSheetMovieRecycleBinding =
        BottomSheetMovieRecycleBinding.inflate(
            LayoutInflater.from(context), this, true
        )

    val backgroundPoster: ImageView = binding.toolbarImage
    val poster: ImageView = binding.posterImageView
    val recycler = binding.recycler

    val appBar = binding.appBar
    val collapsingToolbarLayout: CollapsingToolbarLayout = binding.toolbarLayout
    val toolbar = binding.toolbar

    fun expandToolbar() {
        appBar.setExpanded(true)
    }

    fun collapseToolbar() {
        appBar.setExpanded(false)
    }

    // TODO move sizes to another class
    fun setBackgroundPoster(posterPath: String?) {
        backgroundPoster.visibility = View.VISIBLE
        Picasso.get()
            .load(Private.IMAGES_URL + "w500" + posterPath)
            .error(R.drawable.ic_launcher_foreground)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(backgroundPoster)
    }

    fun setForegroundPoster(posterPath: String?) {
        Picasso.get()
            .load(Private.IMAGES_URL + "w342" + posterPath)
            .error(R.drawable.ic_launcher_foreground)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(poster)
    }
}
