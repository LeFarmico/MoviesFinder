package com.lefarmico.moviesfinder.adapters

import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.activities.MainActivity
import com.lefarmico.moviesfinder.databinding.ItemPlaceholderRecyclerBinding
import com.lefarmico.moviesfinder.models.ItemsDataModel

class ItemsPlaceholderAdapter() : RecyclerView.Adapter<ItemsPlaceholderAdapter.ViewHolder>() {

    private lateinit var itemsDataModel: ItemsDataModel

    private val scrollStates: MutableMap<Long, Parcelable?> = mutableMapOf()

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

        val key = getItemId(holder.layoutPosition)
        val state = scrollStates[key]
        // Корректно ли это?
        holder.recyclerView.apply {
            layoutManager = itemsLayoutManager
            setRecycledViewPool(viewPool)
            if (state != null) {
                layoutManager?.onRestoreInstanceState(state)
            } else {
                layoutManager?.scrollToPosition(0)
            }
            holder.bind(
                ItemAdapter {
                    (context as MainActivity).presenter.onItemClick(it)
                }
            )
            (adapter as ItemAdapter).setItems(itemsDataModel.itemHeaders)
        }
    }

    override fun getItemCount(): Int = 1

    fun setNestedItemsData(itemsDataModel: ItemsDataModel) {
        this.itemsDataModel = itemsDataModel
        notifyDataSetChanged()
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)

        val key = getItemId(holder.layoutPosition)
        scrollStates[key] = holder
            .itemView
            .findViewById<RecyclerView>(R.id.items_placeholder_recycler)
            .layoutManager
            ?.onSaveInstanceState()
    }
}
