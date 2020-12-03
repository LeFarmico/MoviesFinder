package com.lefarmico.moviesfinder.castomViews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.R
import kotlinx.android.synthetic.main.parent_recycler.view.*

class CategoryView(context: Context, @Nullable attributeSet: AttributeSet): LinearLayout(context, attributeSet) {
    private val moviesContainer: RecyclerView

    init {
        LayoutInflater.from(context).inflate(R.layout.parent_recycler, this)
        this.orientation = VERTICAL
        moviesContainer = recycler_child
    }
}