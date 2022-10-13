package com.lefarmico.moviesfinder.ui.common.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.annotation.DimenRes
import androidx.annotation.Nullable
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
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

    private val backgroundPoster: ImageView = binding.toolbarImage
    private val poster: ImageView = binding.posterImageView
    private val recycler = binding.recycler

    private val appBar = binding.appBar
    private val collapsingToolbarLayout: CollapsingToolbarLayout = binding.toolbarLayout
    private val toolbar = binding.toolbar

    private var isScrollEnabled = true
    private var isDraggingEnabled = true

    var onHidden: (BottomSheetBehavior<*>) -> Unit = {}
    var onHalfExpanded: (BottomSheetBehavior<*>) -> Unit = {}
    var onExpanded: (BottomSheetBehavior<*>) -> Unit = {}
    var onDragging: (BottomSheetBehavior<*>) -> Unit = {}
    var onCollapsed: (BottomSheetBehavior<*>) -> Unit = {}
    var onSettling: (BottomSheetBehavior<*>) -> Unit = {}
    var onSlide: (slideOffset: Float) -> Unit = {}

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        recycler.addItemDecoration(
            MovieDetailsDecorator(
                horizontalPd = getPixelOffsetResource(R.dimen.stnd_margin),
                betweenLinePd = getPixelOffsetResource(R.dimen.stnd_between_line_space),
                headerBottomPd = getPixelOffsetResource(R.dimen.stnd_very_small_margin)
            ),
            0
        )
        setAppBarBehavior()
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

    private fun setAppBarBehavior() {
        val layoutParams = binding.appBar.layoutParams
        if (layoutParams is LayoutParams) {
            layoutParams.apply {
                if (behavior == null)
                    behavior = AppBarLayout.Behavior()
                val behavior = behavior as AppBarLayout.Behavior
                behavior.setDragCallback(object : AppBarLayout.Behavior.DragCallback() {
                    override fun canDrag(appBarLayout: AppBarLayout): Boolean = isScrollEnabled
                })
            }
        }
    }

    private fun getPixelOffsetResource(@DimenRes resId: Int): Int {
        return try {
            context.resources.getDimensionPixelOffset(resId)
        } catch (e: RuntimeException) {
            throw e
        }
    }

    fun bindBottomSheetBehaviour() {
        (this.layoutParams as LayoutParams).behavior = BottomSheetBehavior.from(this).apply {
            this.isDraggable = isDraggingEnabled

            addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    onSlide(slideOffset)
                    backgroundPosterAlphaBehaviour(slideOffset)
                }
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    when (newState) {
                        BottomSheetBehavior.STATE_HIDDEN -> onHidden(this@apply)
                        BottomSheetBehavior.STATE_HALF_EXPANDED -> onHalfExpanded(this@apply)
                        BottomSheetBehavior.STATE_EXPANDED -> onExpanded(this@apply)
                        BottomSheetBehavior.STATE_DRAGGING -> onDragging(this@apply)
                        BottomSheetBehavior.STATE_COLLAPSED -> onCollapsed(this@apply)
                        BottomSheetBehavior.STATE_SETTLING -> onSettling(this@apply)
                    }
                }
            })
        }
    }

    private fun isBottomSheet(): Boolean {
        return if (this.layoutParams is LayoutParams) {
            return (this.layoutParams as LayoutParams).behavior is BottomSheetBehavior<*>
        } else {
            false
        }
    }

    fun getBehavior(): BottomSheetBehavior<*> {
        return if (isBottomSheet()) {
            (this.layoutParams as LayoutParams).behavior as BottomSheetBehavior<*>
        } else {
            throw NullPointerException(
                "BottomSheetBehavior is not initialized"
            )
        }
    }

    fun enableScroll() {
        isScrollEnabled = true
        recycler.isNestedScrollingEnabled = true
    }

    fun disableScroll() {
        isScrollEnabled = false
        recycler.isNestedScrollingEnabled = false
    }

    fun enableDragging() {
        isDraggingEnabled = true
        getBehavior().isDraggable = true
    }

    fun disableDragging() {
        getBehavior().isDraggable = false
        isDraggingEnabled = false
    }

    fun onNavigateUpPressed(action: () -> Unit) {
        toolbar.setNavigationOnClickListener {
            action()
            expandToolbar()
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

    // TODO handle different way
    private fun backgroundPosterAlphaBehaviour(alpha: Float) {
        if (isBottomSheet()) {
            if (alpha >= 0.5f) {
                backgroundPoster.alpha = alpha - 0.5f
            }
        } else {
            backgroundPoster.alpha = 1f
        }
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
}
