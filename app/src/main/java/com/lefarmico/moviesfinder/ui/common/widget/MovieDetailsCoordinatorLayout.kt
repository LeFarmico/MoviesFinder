package com.lefarmico.moviesfinder.ui.common.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.ToggleButton
import androidx.annotation.DimenRes
import androidx.annotation.Nullable
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.data.entity.MovieCastData
import com.lefarmico.moviesfinder.data.entity.MovieDetailedData
import com.lefarmico.moviesfinder.databinding.FragmentMovieDetailsBinding
import com.lefarmico.moviesfinder.private.Private
import com.lefarmico.moviesfinder.ui.common.adapter.CastAdapter
import com.lefarmico.moviesfinder.ui.common.adapter.SpinnerProviderAdapter
import com.lefarmico.moviesfinder.ui.common.animation.TextViewCollapseLineAnimator
import com.lefarmico.moviesfinder.ui.common.decorator.PaddingItemDecoration
import com.squareup.picasso.Picasso
import java.util.*

class MovieDetailsCoordinatorLayout(
    context: Context,
    @Nullable attributeSet: AttributeSet
) : CoordinatorLayout(context, attributeSet), DefaultLifecycleObserver {

    private var binding: FragmentMovieDetailsBinding =
        FragmentMovieDetailsBinding.inflate(
            LayoutInflater.from(context), this, true
        )

    private val backgroundPoster: ImageView = binding.toolbarImage
    private val poster: ImageView = binding.posterImageView
    private val movieTitleToolbar: CollapsingToolbarLayout = binding.toolbarLayout
    private val generalRating: RatingView = binding.movieRate
    private val movieDescription: TextView = binding.movieDescription
    private val providerSpinner: Spinner = binding.providerSpinner
    private val movieLength: TextView = binding.length
    private val releaseDate: TextView = binding.releaseDate
    private val genres: TextView = binding.genresTextView
    private val actors: RecyclerView = binding.actorsRecyclerView
    private val isWatchlist: ToggleButton = binding.favoriteToggleButton

    private var isScrollEnabled = true
    private var isDraggingEnabled = true

    var onHidden: (BottomSheetBehavior<*>) -> Unit = {}
    var onHalfExpanded: (BottomSheetBehavior<*>) -> Unit = {}
    var onExpanded: (BottomSheetBehavior<*>) -> Unit = {}
    var onDragging: (BottomSheetBehavior<*>) -> Unit = {}
    var onCollapsed: (BottomSheetBehavior<*>) -> Unit = {}
    var onSettling: (BottomSheetBehavior<*>) -> Unit = {}
    var onSlide: (slideOffset: Float) -> Unit = {}

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        setDecorationCastRecycler(
            PaddingItemDecoration(
                horizontalPd = getPixelOffsetResource(R.dimen.stnd_very_small_margin)
            )
        )
        setAppBarBehavior()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        actors.invalidateItemDecorations()
    }

    private fun setDecorationCastRecycler(vararg decorators: RecyclerView.ItemDecoration) {
        for (i in decorators.indices) {
            actors.addItemDecoration(decorators[i], i)
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
            isDraggable = isDraggingEnabled

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
        binding.nestedScroll.isNestedScrollingEnabled = true
    }

    fun disableScroll() {
        isScrollEnabled = false
        binding.nestedScroll.isNestedScrollingEnabled = false
    }

    fun enableDragging() {
        isDraggingEnabled = true
    }

    fun disableDragging() {
        isDraggingEnabled = false
    }

    fun onNavigateUpPressed(action: () -> Unit) {
        binding.toolbar.setNavigationOnClickListener {
            action()
            expandToolbar()
        }
    }

    fun expandToolbar() {
        binding.appBar.setExpanded(true)
    }

    fun collapseToolbar() {
        binding.appBar.setExpanded(false)
    }

    @SuppressLint("SetTextI18n")
    fun setMovieItem(movieItem: MovieDetailedData) {
        genres.text = concatGenres(movieItem.genres)
        movieTitleToolbar.title = movieItem.title
        releaseDate.text = parseReleaseDate(movieItem.releaseDate)
        generalRating.setRatingValue(movieItem.rating)
        providerSpinner.adapter = SpinnerProviderAdapter(context, movieItem.watchMovieProviderData)
        isWatchlist.isChecked = movieItem.isWatchlist
        movieLength.text = "Length: ${movieItem.length} min"
        setCast(movieItem.actors)
        setPoster(movieItem.posterPath)
        setBackground(movieItem.posterPath)
        setDescription(movieItem.description)
        binding.actorsRecyclerView.isNestedScrollingEnabled = false
    }

    fun watchListCallback(onChecked: () -> Unit, notChecked: () -> Unit) {
        isWatchlist.setOnClickListener {
            if (isWatchlist.isChecked) { onChecked() } else { notChecked() }
        }
    }

    private fun backgroundPosterAlphaBehaviour(alpha: Float) {
        if (isBottomSheet()) {
            if (alpha >= 0.5f) {
                backgroundPoster.alpha = alpha
            }
        } else {
            backgroundPoster.alpha = 1f
        }
    }

    private fun setBackground(posterPath: String?) {
        backgroundPoster.visibility = View.VISIBLE
        Picasso.get()
            .load(Private.IMAGES_URL + "w500" + posterPath)
            .error(R.drawable.ic_launcher_foreground)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(backgroundPoster)
    }

    private fun parseReleaseDate(dateStringFormat: String): String =
        "Release: ${dateStringFormat.split("-")[0]}"

    // TODO delete logic
    private fun concatGenres(genresList: List<String>): StringBuffer =
        StringBuffer().apply {
            genresList.forEachIndexed { index, string ->
                append(string.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() })
                if (index != genresList.size - 1) { append(" / ") }
            }
        }

    private fun setPoster(posterPath: String?) {
        Picasso.get()
            .load(Private.IMAGES_URL + "w342" + posterPath)
            .error(R.drawable.ic_launcher_foreground)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(poster)
    }

    private fun setCast(movieCastData: List<MovieCastData>) {
        if (actors.adapter == null) {
            actors.adapter = CastAdapter().apply { submitList(movieCastData) }
        } else {
            (actors.adapter as CastAdapter).submitList(movieCastData)
        }
    }

    // TODO add collapse on hidden
    private fun setDescription(description: String) {
        movieDescription.apply {
            text = description
            maxLines = 5
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            setOnClickListener {
                TextViewCollapseLineAnimator(movieDescription, 500L, true, 5, context)
                    .start()
            }
        }
    }
}
