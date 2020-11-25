package com.lefarmico.moviesfinder

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.category_line_bar_horizontal.view.*

class CategoryView(context: Context, @Nullable attributeSet: AttributeSet): LinearLayout(context, attributeSet) {
    private val categoryName: TextView
    private val moviesContainer: RecyclerView

    init {
        LayoutInflater.from(context).inflate(R.layout.category_line_bar_horizontal, this)
        this.orientation = VERTICAL
        categoryName = category_name
        moviesContainer = movies_horizontal_container
    }
}