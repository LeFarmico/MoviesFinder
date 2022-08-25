package com.lefarmico.moviesfinder.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.data.appEntity.Cast
import com.lefarmico.moviesfinder.databinding.ItemCastBinding
import com.lefarmico.moviesfinder.extension.loadWithThemeParams
import com.lefarmico.moviesfinder.private.ApiConstants
import com.squareup.picasso.Picasso

class CastAdapter : ListAdapter<Cast, CastAdapter.ViewHolder>(
    CastDuffUtilCallback()
) {

    class ViewHolder(itemCastBinding: ItemCastBinding) : RecyclerView.ViewHolder(itemCastBinding.root) {
        private val personName: TextView = itemCastBinding.personName
        private val poster: ImageView = itemCastBinding.poster
        private val character: TextView = itemCastBinding.character

        fun bind(cast: Cast) {
            personName.text = cast.name
            character.text = cast.character

            Picasso
                .get()
                .loadWithThemeParams(ApiConstants.IMAGES_URL + "w342" + cast.profilePath)
                .into(poster)
        }
    }

    private class CastDuffUtilCallback : DiffUtil.ItemCallback<Cast>() {
        override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
            return oldItem.personId == newItem.personId
        }

        override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
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
