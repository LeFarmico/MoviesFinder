package com.lefarmico.moviesfinder.ui.common.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.annotation.Nullable
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.data.entity.MovieDetailedData
import com.lefarmico.moviesfinder.databinding.BottomSheetMovieRecycleBinding
import com.lefarmico.moviesfinder.private.Private
import com.lefarmico.moviesfinder.ui.common.adapter.MovieDetailsAdapter
import com.lefarmico.moviesfinder.ui.common.adapter.decorator.MovieDetailsDecorator
import com.squareup.picasso.Picasso

class MovieCoordinatorLayout(
    context: Context,
    @Nullable attributeSet: AttributeSet
) : CoordinatorLayout(context, attributeSet), DefaultLifecycleObserver {

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

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        recycler.addItemDecoration(
            MovieDetailsDecorator(
                horizontalPd = R.dimen.stnd_margin.pixelOffset,
                betweenLinePd = R.dimen.stnd_between_line_space.pixelOffset,
                headerBottomPd = R.dimen.stnd_very_small_margin.pixelOffset
            ),
            0
        )
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        recycler.invalidateItemDecorations()
        recycler.adapter = null
    }

    fun setRecyclerAdapter(movieDetailsAdapter: MovieDetailsAdapter) {
        if (recycler.adapter == null) {
            recycler.adapter = movieDetailsAdapter
        } else {
            Log.i(this::class.simpleName, "Adapter already set.")
        }
    }

    fun expandToolbar() {
        appBar.setExpanded(true)
    }

    fun collapseToolbar() {
        appBar.setExpanded(false)
    }

    @SuppressLint("SetTextI18n")
    fun setMovieItem(movieItem: MovieDetailedData) {
        collapsingToolbarLayout.title = movieItem.title
        setPoster(movieItem.posterPath)
        setBackground(movieItem.posterPath)
    }

    // TODO move sizes to another class
    private fun setBackground(posterPath: String?) {
        backgroundPoster.visibility = View.VISIBLE
        Picasso.get()
            .load(Private.IMAGES_URL + "w500" + posterPath)
            .error(R.drawable.ic_launcher_foreground)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(backgroundPoster)
    }

    private fun setPoster(posterPath: String?) {
        Picasso.get()
            .load(Private.IMAGES_URL + "w342" + posterPath)
            .error(R.drawable.ic_launcher_foreground)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(poster)
    }

    private val Int.pixelOffset: Int get() = context.resources.getDimensionPixelOffset(this)
}
