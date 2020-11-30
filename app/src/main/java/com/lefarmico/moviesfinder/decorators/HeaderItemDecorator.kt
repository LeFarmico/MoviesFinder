package com.lefarmico.moviesfinder.decorators

import android.graphics.Canvas
import androidx.recyclerview.widget.RecyclerView


class HeaderItemDecorator : RecyclerView.ItemDecoration(){

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val topChild = parent.getChildAt(0) ?: return

        val topChildPosition = parent.getChildAdapterPosition(topChild)
        if (topChildPosition == RecyclerView.NO_POSITION)
            return
    }
}