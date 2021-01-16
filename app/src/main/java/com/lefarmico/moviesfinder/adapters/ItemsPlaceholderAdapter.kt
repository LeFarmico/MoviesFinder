package com.lefarmico.moviesfinder.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.data.Item
import com.lefarmico.moviesfinder.data.ItemsData
import com.lefarmico.moviesfinder.databinding.ItemPlaceholderRecyclerBinding

// TODO : лямбда на setOnItemClickListener
class ItemsPlaceholderAdapter() : RecyclerView.Adapter<ItemsPlaceholderAdapter.ViewHolder>() {

    private lateinit var itemsData: ItemsData

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

        // Для плавного горизонтального скролла
        val viewPool = RecyclerView.RecycledViewPool()
        val itemsLayoutManager = LinearLayoutManager(
            holder.recyclerView.context, RecyclerView.HORIZONTAL, false
        )

        holder.recyclerView.apply {
            layoutManager = itemsLayoutManager
            adapter = ItemAdapter(object : ItemAdapter.OnItemClick {
                override fun onClick(item: Item) {

                    // Нужно прокинуть его в активити, или что-то вроде того
                }
            })
            (adapter as ItemAdapter).setItems(itemsData.items)

            setRecycledViewPool(viewPool)
        }
    }

    override fun getItemCount(): Int = 1

    fun setNestedItemsData(itemsData: ItemsData) {
        this.itemsData = itemsData
    }
}
