package com.lefarmico.moviesfinder.ui.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.data.entity.MovieProviderData
import com.lefarmico.moviesfinder.databinding.ItemProviderBinding
import com.lefarmico.moviesfinder.private.Private
import com.squareup.picasso.Picasso

class ProviderAdapter : RecyclerView.Adapter<ProviderAdapter.ViewHolder>() {

    private var items: List<MovieProviderData> = emptyList()

    class ProviderDiffUtil(
        private val oldList: List<MovieProviderData>,
        private val newList: List<MovieProviderData>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] === newList[newItemPosition]

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] == newList[newItemPosition]
    }

    class ViewHolder(
        providerRecyclerBinding: ItemProviderBinding
    ) : RecyclerView.ViewHolder(providerRecyclerBinding.root) {
        private val imageView: ImageView = providerRecyclerBinding.providerImageView
        private val providerNameTextView = providerRecyclerBinding.providerName

        fun bind(imageResource: String, providerName: String) {
            providerNameTextView.text = providerName
            Picasso
                .get()
                .load(Private.IMAGES_URL + "w92" + imageResource)
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

    fun setItems(items: List<MovieProviderData>) {
        val oldField = this.items
        this.items = items
        val diffCallback = ProviderDiffUtil(oldField, items)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
    }
}
