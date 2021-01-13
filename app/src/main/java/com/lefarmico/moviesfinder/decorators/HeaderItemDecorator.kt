
package com.lefarmico.moviesfinder.decorators

import android.graphics.Canvas
import android.view.MotionEvent
import androidx.core.view.doOnNextLayout
import androidx.recyclerview.widget.RecyclerView

class HeaderItemDecorator(
    parent: RecyclerView,
    private val shouldFadeOutHeader: Boolean = false,
    private val isHeader: (itemPosition: Int) -> Boolean
) : RecyclerView.ItemDecoration() {

    private var currentHeader: Pair<Int, RecyclerView.ViewHolder>? = null

    init {
        parent.adapter?.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                currentHeader = null
            }
        })
        parent.doOnNextLayout {
            currentHeader = null
        }
        parent.addOnItemTouchListener(object : RecyclerView.SimpleOnItemTouchListener() {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                return if (e.action == MotionEvent.ACTION_DOWN) {
                    e.y <= currentHeader?.second?.itemView?.bottom ?: 0
                } else false
            }
        })
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val topChild = parent.getChildAt(0) ?: return

        val topChildPosition = parent.getChildAdapterPosition(topChild)
        if (topChildPosition == RecyclerView.NO_POSITION)
            return
    }
}
