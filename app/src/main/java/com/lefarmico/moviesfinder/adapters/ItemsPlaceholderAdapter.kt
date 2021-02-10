package com.lefarmico.moviesfinder.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.activities.MainActivity
import com.lefarmico.moviesfinder.models.ItemsDataModel
import com.lefarmico.moviesfinder.databinding.ItemPlaceholderRecyclerBinding
import com.lefarmico.moviesfinder.fragments.DetailsFragment

class ItemsPlaceholderAdapter() : RecyclerView.Adapter<ItemsPlaceholderAdapter.ViewHolder>() {

    private lateinit var itemsDataModel: ItemsDataModel

    class ViewHolder(
        placeholderBinding: ItemPlaceholderRecyclerBinding
    ) : RecyclerView.ViewHolder(placeholderBinding.root) {

        var recyclerView: RecyclerView = placeholderBinding.itemsPlaceholderRecycler

        fun bind(adapter: ItemAdapter) {
            recyclerView.adapter = adapter
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
        // Для плавного горизонтального скролла
        val viewPool = RecyclerView.RecycledViewPool()
        val itemsLayoutManager = LinearLayoutManager(
            holder.recyclerView.context, RecyclerView.HORIZONTAL, false
        )

        // Корректно ли это?
        holder.recyclerView.apply {
            layoutManager = itemsLayoutManager
            setRecycledViewPool(viewPool)
            holder.bind(
                ItemAdapter {
                    Toast.makeText(context, it.title, Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, DetailsFragment::class.java)
                    intent.putExtra("movie", it)
//                    (context as MainActivity).launchDetailsFragment(it)
                    (context as MainActivity).launchMovieDetailsBottomSheet(it)
                }
            )
            (adapter as ItemAdapter).setItems(itemsDataModel.items)
        }
    }

    override fun getItemCount(): Int = 1

    fun setNestedItemsData(itemsDataModel: ItemsDataModel) {
        this.itemsDataModel = itemsDataModel
        notifyDataSetChanged()
    }
}
