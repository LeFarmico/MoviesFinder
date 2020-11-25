package com.lefarmico.moviesfinder.groupie

import com.lefarmico.moviesfinder.R
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.movie_item.*


class MovieItem(val movieContent: MovieContent) : Item(){

    override fun getLayout() = R.layout.movie_item

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.poster.setImageResource(movieContent.poster)
        viewHolder.movie_title.text = movieContent.title
        viewHolder.itemView.setOnClickListener {

        }
    }

}
data class MovieContent(val poster: Int, val title: String)