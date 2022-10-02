package com.lefarmico.moviesfinder.ui.main.adapter.decorator

import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.R

class MovieDetailsDecorator(
    private val horizontalPd: Int = 0,
    private val betweenLinePd: Int = 0,
    private val dimenType: Int = PX,
) : RecyclerView.ItemDecoration() {

    private val Int.convertPx: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        when {
            view == parent.findViewById(R.id.header_with_recycler) -> {}
            view == parent.findViewById(R.id.movie_overview) -> {}
            else -> {
                when (dimenType) {
                    PX -> {
                        outRect.right = horizontalPd
                        outRect.left = horizontalPd
                        outRect.bottom = betweenLinePd
                        outRect.top = 0
                    }
                    DP -> {
                        outRect.right = horizontalPd.convertPx
                        outRect.left = horizontalPd.convertPx
                        outRect.bottom = betweenLinePd.convertPx
                        outRect.top = 0
                    }
                }
            }
        }
    }

    companion object {
        const val PX = 0
        const val DP = 1
    }

    enum class ItemPos {
        FIRST, LAST
    }
}
