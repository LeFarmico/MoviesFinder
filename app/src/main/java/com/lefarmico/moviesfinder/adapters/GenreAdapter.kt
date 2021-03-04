package com.lefarmico.moviesfinder.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.databinding.GenreItemBinding

class GenreAdapter : RecyclerView.Adapter<GenreAdapter.ViewHolder>() {

    private lateinit var genreData: List<String>

    class ViewHolder(genreItemBinding: GenreItemBinding) : RecyclerView.ViewHolder(genreItemBinding.root) {
        private val genreText: TextView = genreItemBinding.genreTextView
        private val genreImage: ImageView = genreItemBinding.genreImageView

        fun bind(genreItem: String) {
            genreText.text = genreItem
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            GenreItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(genreData[position])
    }

    override fun getItemCount(): Int = genreData.size

    fun setItems(list: List<String>) {
        genreData = list
        notifyDataSetChanged()
    }
}
