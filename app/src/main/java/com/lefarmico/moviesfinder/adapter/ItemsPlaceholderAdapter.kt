package com.lefarmico.moviesfinder.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.data.entity.CategoryAndMovieBriefData
import com.lefarmico.moviesfinder.data.entity.MovieBriefData
import com.lefarmico.moviesfinder.databinding.ItemPlaceholderRecyclerBinding

class ItemsPlaceholderAdapter(
    private val onMovieClick: (MovieBriefData) -> Unit
) : RecyclerView.Adapter<ItemsPlaceholderAdapter.ViewHolder>() {

    var itemsList: List<CategoryAndMovieBriefData> = emptyList()
        set(value) {
            val oldField = field
            field = value
            val diffCallback = ItemPlaceholderDiffUtil(oldField, field)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            diffResult.dispatchUpdatesTo(this)
        }

    class ItemPlaceholderDiffUtil(
        private val oldList: List<CategoryAndMovieBriefData>,
        private val newList: List<CategoryAndMovieBriefData>,
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] === newList[newItemPosition]

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] == newList[newItemPosition]
    }

    class ViewHolder(
        placeholderBinding: ItemPlaceholderRecyclerBinding
    ) : RecyclerView.ViewHolder(placeholderBinding.root) {

        private val itemRecyclerView = placeholderBinding.itemsList
        private val headerTitleTextView = placeholderBinding.headerTitle

        fun bind(
            movieBriefDataList: List<MovieBriefData>,
            categoryName: String,
            onClick: (MovieBriefData) -> Unit
        ) {
            headerTitleTextView.text = categoryName
            itemRecyclerView.adapter = ItemAdapter(onClick).apply {
                items = movieBriefDataList
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

        val item = itemsList[position]
        val categoryName = holder.itemView.context.getString(item.categoryData.categoryResourceId)
        holder.bind(
            item.movieBriefDataList,
            categoryName,
            onMovieClick
        )
    }

    override fun getItemCount(): Int = itemsList.size

//    fun setItems(itemsList: MutableList<CategoryAndMovieBriefData>) {
//        this.itemsList = itemsList
//        notifyDataSetChanged()
//    }
//
//    fun addItems(itemsList: MutableList<MovieBriefData>) {
//        val curItems = this.itemsList
//        this.itemsList = (curItems + itemsList).toMutableList()
//        // TODO Change it
//        notifyDataSetChanged()
//    }

//    override fun onViewRecycled(holder: ViewHolder) {
//        super.onViewRecycled(holder)
//        val key = getItemId(holder.layoutPosition)
//        scrollStates[key] = holder
//            .itemView
//            .findViewById<RecyclerView>(R.id.items_placeholder_recycler)
//            .layoutManager
//            ?.onSaveInstanceState()
//    }
}
