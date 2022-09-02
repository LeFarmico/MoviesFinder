package com.lefarmico.moviesfinder.ui.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.data.entity.MovieCastData
import com.lefarmico.moviesfinder.databinding.ItemCastBinding
import com.lefarmico.moviesfinder.private.ApiConstants
import com.lefarmico.moviesfinder.utils.extension.loadWithThemeParams
import com.squareup.picasso.Picasso

class CastAdapter : ListAdapter<MovieCastData, CastAdapter.ViewHolder>(
    CastDuffUtilCallback()
) {

    class ViewHolder(itemCastBinding: ItemCastBinding) : RecyclerView.ViewHolder(itemCastBinding.root) {
        private val personName: TextView = itemCastBinding.personName
        private val poster: ImageView = itemCastBinding.poster
        private val character: TextView = itemCastBinding.character

        fun bind(movieCastData: MovieCastData) {
            personName.text = movieCastData.name
            character.text = movieCastData.character

            Picasso
                .get()
                .loadWithThemeParams(ApiConstants.IMAGES_URL + "w342" + movieCastData.profilePath)
                .into(poster)
        }
    }

    private class CastDuffUtilCallback : DiffUtil.ItemCallback<MovieCastData>() {
        override fun areItemsTheSame(oldItem: MovieCastData, newItem: MovieCastData): Boolean {
            return oldItem.personId == newItem.personId
        }

        override fun areContentsTheSame(oldItem: MovieCastData, newItem: MovieCastData): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemCastBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val castItem = getItem(position)
        holder.bind(castItem)
    }
}
