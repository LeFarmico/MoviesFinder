package com.lefarmico.moviesfinder.ui.common.decorator

import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class PaddingItemDecoration constructor(
    private val leftPd: Int = 0,
    private val rightPd: Int = 0,
    private val topPd: Int = 0,
    private val bottomPd: Int = 0,
    private val dimenType: Int = PX
) : RecyclerView.ItemDecoration() {

    private val Int.convertPx: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    constructor(
        horizontalPd: Int = 0,
        verticalPd: Int = 0
    ) : this(
        leftPd = horizontalPd,
        rightPd = horizontalPd,
        topPd = verticalPd,
        bottomPd = verticalPd,
        dimenType = PX
    )

    constructor(
        layoutPd: Int = 0
    ) : this(
        leftPd = layoutPd,
        rightPd = layoutPd,
        topPd = layoutPd,
        bottomPd = layoutPd,
        dimenType = PX
    )

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        when (dimenType) {
            PX -> {
                outRect.right = rightPd
                outRect.left = leftPd
                outRect.top = topPd
                outRect.bottom = bottomPd
            }
            DP -> {
                outRect.right = rightPd.convertPx
                outRect.left = leftPd.convertPx
                outRect.top = topPd.convertPx
                outRect.bottom = bottomPd.convertPx
            }
            else -> {
                throw IllegalArgumentException("incorrect dimenType: $dimenType")
            }
        }
    }

    companion object {
        const val PX = 0
        const val DP = 1
    }
}
