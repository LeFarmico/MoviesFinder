package com.lefarmico.moviesfinder.decorators

import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class TopSpacingItemDecoration(private val paddingInDp: Int) : RecyclerView.ItemDecoration() {
    private val Int.convertPx: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = paddingInDp.convertPx
        outRect.right = paddingInDp.convertPx
        outRect.left = paddingInDp.convertPx
    }
}
