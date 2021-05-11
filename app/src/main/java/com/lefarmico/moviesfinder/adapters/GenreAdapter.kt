package com.lefarmico.moviesfinder.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.databinding.ItemGenreBinding

class GenreAdapter : RecyclerView.Adapter<GenreAdapter.ViewHolder>() {

    private lateinit var genreData: List<String>

    class ViewHolder(itemGenreBinding: ItemGenreBinding) : RecyclerView.ViewHolder(itemGenreBinding.root) {
        private val genreText: TextView = itemGenreBinding.genreTextView
        val slash: TextView = itemGenreBinding.slash

        fun bind(genreItem: String) {
            genreText.text = genreItem
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemGenreBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(genreData[position])
        if (position + 1 == itemCount) {
            holder.slash.visibility = View.INVISIBLE
        }
    }

    override fun getItemCount(): Int = genreData.size

    fun setItems(list: List<String>) {
        genreData = list
        notifyDataSetChanged()
    }
}
