
package com.lefarmico.moviesfinder.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.databinding.ItemHeaderBinding

class TitleGroupAdapter : RecyclerView.Adapter<TitleGroupAdapter.ViewHolder>() {

    // TODO : Inject to model
    private val items = mutableListOf<Int>()

    class ViewHolder(
        itemHeaderBinding: ItemHeaderBinding
    ) : RecyclerView.ViewHolder(itemHeaderBinding.root) {
        private val headerTitle: TextView = itemHeaderBinding.headerTitle
        fun bind(@StringRes titleRes: Int) {
            val title = itemView.context.getString(titleRes)
            headerTitle.text = title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemHeaderBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("TAG", "bind, position = $position")
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = 1

    fun setHeaderTitle(title: Int) {
        items.add(title)
        // TODO change it
        notifyDataSetChanged()
    }
}
