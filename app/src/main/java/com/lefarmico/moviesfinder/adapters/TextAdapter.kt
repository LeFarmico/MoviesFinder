package com.lefarmico.moviesfinder.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.databinding.ItemTextBinding

class TextAdapter : RecyclerView.Adapter<TextAdapter.ViewHolder>() {

    private lateinit var genreData: List<String>

    class ViewHolder(itemGenreBinding: ItemTextBinding) : RecyclerView.ViewHolder(itemGenreBinding.root) {
        private val genreText: TextView = itemGenreBinding.genreTextView

        fun bind(genreItem: String) {
            genreText.text = genreItem
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemTextBinding.inflate(
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
