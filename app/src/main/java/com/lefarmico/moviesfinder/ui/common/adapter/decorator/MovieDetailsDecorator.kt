package com.lefarmico.moviesfinder.ui.common.adapter.decorator

import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.R

class MovieDetailsDecorator(
    private val horizontalPd: Int = 0,
    private val betweenLinePd: Int = 0,
    private val headerBottomPd: Int = 0,
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
            view == parent.findViewById(R.id.widget_recycler) -> outRect.setPaddings(
                bottom = betweenLinePd,
                dimenType = dimenType
            )
            view == parent.findViewById(R.id.movie_overview) -> {}
            view == parent.findViewById(R.id.header) -> outRect.setPaddings(
                right = horizontalPd,
                left = horizontalPd,
                bottom = headerBottomPd,
                top = 0,
                dimenType = dimenType
            )
            else -> outRect.setPaddings(
                right = horizontalPd,
                left = horizontalPd,
                bottom = betweenLinePd,
                top = 0,
                dimenType = dimenType
            )
        }
    }

    private fun Rect.setPaddings(
        left: Int = 0,
        right: Int = 0,
        top: Int = 0,
        bottom: Int = 0,
        dimenType: Int = PX
    ) {
        if (dimenType != PX && dimenType != DP) {
            throw IllegalArgumentException("Incorrect dimenType: dimenType = $dimenType")
        }
        if (left < 0 || right < 0 || top < 0 || bottom < 0) {
            throw IllegalArgumentException(
                "Paddings have to be >= 0:" +
                    " left = $left, right = $right, top = $top, bottom = $bottom"
            )
        }
        when (dimenType) {
            PX -> {
                this.right = right
                this.left = left
                this.bottom = bottom
                this.top = top
            }
            DP -> {
                this.right = right.convertPx
                this.left = left.convertPx
                this.bottom = bottom.convertPx
                this.top = top.convertPx
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
