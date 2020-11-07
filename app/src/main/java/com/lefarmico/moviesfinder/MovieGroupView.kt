package com.lefarmico.moviesfinder

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.annotation.Nullable
import kotlinx.android.synthetic.main.category_line_bar_horizontal.view.*

class MovieGroupView(context: Context, @Nullable attributeSet: AttributeSet) :
    LinearLayout(context, attributeSet) {
    private val movieButtonsList: GridLayout
    private val expandButton: Button


    init {
        LayoutInflater.from(context).inflate(R.layout.category_line_bar_horizontal, this)

        this.orientation = VERTICAL

        expandButton = category_name
        movieButtonsList = movie_buttons_list

        expandButton.setOnClickListener {
            if(movieButtonsList.orientation == HORIZONTAL ){
                movieButtonsList.orientation = GridLayout.VERTICAL
            }
            else {
                movieButtonsList.orientation = GridLayout.HORIZONTAL
            }
        }
    }
}