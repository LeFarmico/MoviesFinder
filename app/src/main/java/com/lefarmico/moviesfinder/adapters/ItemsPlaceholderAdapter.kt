package com.lefarmico.moviesfinder.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.data.ItemsData
import com.lefarmico.moviesfinder.databinding.ItemPlaceholderRecyclerBinding

// TODO : лямбда на setOnItemClickListener
class ItemsPlaceholderAdapter() : RecyclerView.Adapter<ItemsPlaceholderAdapter.ViewHolder>() {

    // TODO : Inject to model
    private lateinit var itemsData: ItemsData

    class ViewHolder(
        placeholderBinding: ItemPlaceholderRecyclerBinding
    ) : RecyclerView.ViewHolder(placeholderBinding.root) {

        var recyclerView: RecyclerView = placeholderBinding.itemsPlaceholderRecycler

        fun bind(itemsData: ItemsData) {
            // Для плавного горизонтального скролла
            val viewPool = RecyclerView.RecycledViewPool()

            val itemsLayoutManager = LinearLayoutManager(
                recyclerView.context, RecyclerView.HORIZONTAL, false
            )
            itemsLayoutManager.initialPrefetchItemCount = 4
            recyclerView.apply {
                layoutManager = itemsLayoutManager
                adapter = ItemAdapter()
                (adapter as ItemAdapter).setItems(itemsData.items)
                setRecycledViewPool(viewPool)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemPlaceholderRecyclerBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("ItemsPlaceHolderAdapter", "${javaClass.name} bind")
        holder.bind(itemsData)
    }

    override fun getItemCount(): Int = 1

    fun setItems(itemsData: ItemsData) {
        this.itemsData = itemsData
        notifyDataSetChanged()
    }
}
