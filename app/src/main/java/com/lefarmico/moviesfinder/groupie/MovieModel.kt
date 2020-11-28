package com.lefarmico.moviesfinder.groupie

import com.lefarmico.moviesfinder.R
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.child_recycler.*


class MovieModel(val movieContent: MovieContent) : Item(){

    override fun getLayout() = R.layout.child_recycler

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.poster.setImageResource(movieContent.poster)
        viewHolder.movie_title.text = movieContent.title
        viewHolder.itemView.setOnClickListener {

        }
    }

}
data class MovieContent(val poster: Int, val title: String)