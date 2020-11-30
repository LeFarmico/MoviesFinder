package com.lefarmico.moviesfinder.adapterDelegates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.adapterDelegates.container.Container

import com.lefarmico.moviesfinder.adapterDelegates.container.ParentModel
import kotlinx.android.synthetic.main.parent_recycler.view.*

class ContainerDelegateAdapter: AbsListItemAdapterDelegate<ParentModel, Container, ContainerDelegateAdapter.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val category: TextView = itemView.findViewById(R.id.category_name)
        var recyclerView: RecyclerView = itemView.recycler_child
    }

    //Для плавного вертикального скролла
    private val viewPool = RecyclerView.RecycledViewPool()

    override fun isForViewType(item: Container, items: MutableList<Container>, position: Int): Boolean {
        return item is ParentModel
    }
    //Создаем ViewHolder на основе Layout (parent_recycler)
    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.parent_recycler, parent, false)
        )
    }
    //Заполняем recycler холдерами
    override fun onBindViewHolder(item: ParentModel, holder: ViewHolder, payloads: MutableList<Any>) {
        holder.category.text = item.categoryName
        val childLayoutManager = LinearLayoutManager(
            holder.recyclerView.context,
            RecyclerView.HORIZONTAL,
        false)

        childLayoutManager.initialPrefetchItemCount = 4

        holder.recyclerView.apply {
            layoutManager = childLayoutManager
            adapter = ItemAdapter()
            (adapter as ItemAdapter).items = item.items //Передаем items из ParentAdapter (Очень важно)
            setRecycledViewPool(viewPool)
            layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.starting_posters_animation_layout)
            scheduleLayoutAnimation()
        }
        holder.category.setOnClickListener{
            if (holder.recyclerView.layoutManager is GridLayoutManager)
                holder.recyclerView.layoutManager = LinearLayoutManager(
                    holder.recyclerView.context,
                    RecyclerView.HORIZONTAL,
                    false)
            else {
                holder.recyclerView.layoutManager = GridLayoutManager(
                    holder.recyclerView.context,
                    3,
                    RecyclerView.VERTICAL,
                    false
                )
                holder.recyclerView.layoutParams
            }
        }

    }
}