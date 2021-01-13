
package com.lefarmico.moviesfinder.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.databinding.ItemPlaceholderRecyclerBinding

class MoviePlaceholderView(context: Context, @Nullable attributeSet: AttributeSet) : LinearLayout(context, attributeSet) {

    private val binding: ItemPlaceholderRecyclerBinding =
        ItemPlaceholderRecyclerBinding.inflate(LayoutInflater.from(context), this, true)

    private val moviesContainer: RecyclerView

    init {
        LayoutInflater.from(context).inflate(R.layout.item_placeholder_recycler, this)
        this.orientation = VERTICAL
        moviesContainer = binding.itemsPlaceholderRecycler
    }
}
