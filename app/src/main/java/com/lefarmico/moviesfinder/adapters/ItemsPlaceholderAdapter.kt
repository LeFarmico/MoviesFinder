package com.lefarmico.moviesfinder.adapters

import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.activities.MainActivity
import com.lefarmico.moviesfinder.data.appEntity.ItemHeaderImpl
import com.lefarmico.moviesfinder.databinding.ItemPlaceholderRecyclerBinding
import com.lefarmico.moviesfinder.providers.CategoryProvider
import com.lefarmico.moviesfinder.utils.PaginationOnScrollListener
import com.lefarmico.moviesfinder.viewModels.MovieFragmentViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemsPlaceholderAdapter(
    val viewModel: MovieFragmentViewModel
) : RecyclerView.Adapter<ItemsPlaceholderAdapter.ViewHolder>() {

    var itemsList: MutableList<ItemHeaderImpl> = mutableListOf()

    private val scrollStates: MutableMap<Long, Parcelable?> = mutableMapOf()
    lateinit var categoryType: CategoryProvider.Category
    var page = 1

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

        holder.recyclerView.apply {
            layoutManager = itemsLayoutManager
            setRecycledViewPool(viewPool)
            if (state != null) {
                layoutManager?.onRestoreInstanceState(state)
            } else {
                layoutManager?.scrollToPosition(0)
            }
            val scope = CoroutineScope(Dispatchers.IO)
            holder.bind(
                ItemAdapter {
                    scope.launch {
                        (context as MainActivity).viewModel.onItemClick(it)
                    }
                }
            )
            (adapter as ItemAdapter).setItems(itemsList)
            addOnScrollListener(
                PaginationOnScrollListener(this.layoutManager!!) {
                    scope.launch {
                        // TODO: Не добавляет объекты
                        viewModel.addPaginationItems(categoryType, ++page, this@ItemsPlaceholderAdapter)
                    }
                }
            )
        }
    }

    override fun getItemCount(): Int = 1

    fun setItems(itemsList: MutableList<ItemHeaderImpl>) {
        this.itemsList = itemsList
        notifyDataSetChanged()
    }

    fun addItems(itemsList: MutableList<ItemHeaderImpl>) {
        val curItems = this.itemsList
        this.itemsList = (curItems + itemsList).toMutableList()
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
