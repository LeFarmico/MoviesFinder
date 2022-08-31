package com.lefarmico.moviesfinder.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.data.appEntity.Provider
import com.lefarmico.moviesfinder.databinding.ItemProviderBinding
import com.lefarmico.moviesfinder.private.ApiConstants
import com.squareup.picasso.Picasso

class ProviderAdapter : RecyclerView.Adapter<ProviderAdapter.ViewHolder>() {

    private var items = listOf<Provider>()

    class ViewHolder(
        providerRecyclerBinding: ItemProviderBinding
    ) : RecyclerView.ViewHolder(providerRecyclerBinding.root) {
        private val imageView: ImageView = providerRecyclerBinding.providerImageView
        private val providerNameTextView = providerRecyclerBinding.providerName

        fun bind(imageResource: String, providerName: String) {
            providerNameTextView.text = providerName
            Picasso
                .get()
                .load(ApiConstants.IMAGES_URL + "w92" + imageResource)
                .fit()
                .error(R.drawable.ic_launcher_foreground)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemProviderBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item.logoPath, item.name)
    }

    override fun getItemCount(): Int = items.size

    fun setItems(providersList: List<Provider>) {
        items = providersList
        // TODO change it
        notifyDataSetChanged()
    }
}
