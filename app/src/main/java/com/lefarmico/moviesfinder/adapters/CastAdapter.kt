package com.lefarmico.moviesfinder.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.data.appEntity.Cast
import com.lefarmico.moviesfinder.databinding.ItemCastBinding
import com.lefarmico.moviesfinder.private.ApiConstants
import com.squareup.picasso.Picasso

class CastAdapter : RecyclerView.Adapter<CastAdapter.ViewHolder>() {

    private lateinit var castList: List<Cast>

    class ViewHolder(itemCastBinding: ItemCastBinding) : RecyclerView.ViewHolder(itemCastBinding.root) {
        private val personName: TextView = itemCastBinding.personName
        private val poster: ImageView = itemCastBinding.poster
        private val character: TextView = itemCastBinding.character

        fun bind(cast: Cast) {
            personName.text = cast.name
            character.text = cast.character

            Picasso
                .get()
                .load(ApiConstants.IMAGES_URL + "w342" + cast.profilePath)
                .fit()
                .error(R.drawable.ic_launcher_foreground)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(poster)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemCastBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(castList[position])
    }

    override fun getItemCount(): Int = castList.size

    fun setItems(list: List<Cast>) {
        castList = list
        notifyDataSetChanged()
    }
}
