
package com.lefarmico.moviesfinder.adapters

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.databinding.ItemHeaderBinding

class HeaderAdapter : RecyclerView.Adapter<HeaderAdapter.ViewHolder>() {

    // TODO : Inject to model
    private val items = mutableListOf<String>()

    class ViewHolder(
        itemHeaderBinding: ItemHeaderBinding
    ) : RecyclerView.ViewHolder(itemHeaderBinding.root) {
        private val headerTitle: TextView = itemHeaderBinding.headerTitle
        val shader: Shader = LinearGradient(
            0.0f, 0.0f, 500f, headerTitle.textSize,
            intArrayOf(
                Color.WHITE,
                Color.parseColor("#ffcc0000"),
                Color.parseColor("#ffff8800")
            ),
            null,
            Shader.TileMode.REPEAT
        )

        fun bind(title: String) {
            headerTitle.text = title
            headerTitle.paint.shader = shader
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

    fun addItem(title: String) {
        items.add(title)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(items.size)
    }
}
