package com.lefarmico.moviesfinder.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.data.ItemsData
import com.lefarmico.moviesfinder.databinding.ItemPlaceholderRecyclerBinding

// TODO : лямбда на setOnItemClickListener
class ItemsPlaceholderAdapter(
) : RecyclerView.Adapter<ItemsPlaceholderAdapter.ViewHolder>() {

    var id: Int = -1
    private lateinit var itemsData : ItemsData

    class ViewHolder(
        placeholderBinding: ItemPlaceholderRecyclerBinding
    ) : RecyclerView.ViewHolder(placeholderBinding.root) {

        var recyclerView: RecyclerView = placeholderBinding.itemsPlaceholderRecycler
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemPlaceholderRecyclerBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("ItemsPlaceHolderAdapter", "${javaClass.name} bind")

        // TODO : Вынести в presenter
        // Для плавного горизонтального скролла
        val viewPool = RecyclerView.RecycledViewPool()
        val itemsLayoutManager = LinearLayoutManager(
            holder.recyclerView.context, RecyclerView.HORIZONTAL, false
        )
        itemsLayoutManager.initialPrefetchItemCount = 4

        holder.recyclerView.apply {
            layoutManager = itemsLayoutManager
            adapter = ItemAdapter()
            (adapter as ItemAdapter).setItems(itemsData.items)

            setRecycledViewPool(viewPool)
        }
    }
    fun setNestedItemsData(itemsData: ItemsData) {
        this.itemsData = itemsData
    }
    override fun getItemCount(): Int = 1
}
