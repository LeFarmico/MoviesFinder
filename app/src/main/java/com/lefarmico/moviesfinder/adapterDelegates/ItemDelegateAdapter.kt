package com.lefarmico.moviesfinder.adapterDelegates

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.lefarmico.moviesfinder.DetailsActivity
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.adapterDelegates.item.Item
import com.lefarmico.moviesfinder.adapterDelegates.item.MovieItem
import kotlinx.android.synthetic.main.child_recycler.view.*

class ItemDelegateAdapter(): AbsListItemAdapterDelegate<MovieItem, Item, ItemDelegateAdapter.ViewHolder>() {
    //Создаем холдер, для связи с view из Item
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val poster: ImageView = itemView.poster
        val title: TextView = itemView.movie_title
    }

    //Определяем тип Item
    override fun isForViewType(item: Item, items: MutableList<Item>, position: Int): Boolean {
        return item is MovieItem
    }


    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        return  ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.child_recycler, parent, false))
    }

    override fun onBindViewHolder(item: MovieItem, holder: ViewHolder, payloads: MutableList<Any>) {
        holder.poster.setImageResource(item.poster)
        holder.title.text = item.title

        setOnItemClick(holder, item)
    }

    private fun setOnItemClick(holder: ViewHolder, item: MovieItem) {
        holder.poster.setOnClickListener {
            //Создаем бандл и кладем туда объект с данными фильма
            val bundle = Bundle()
            //Первым параметром указывается ключ, по которому потом будем искать, вторым сам
            //передаваемый объект
            bundle.putParcelable("movieItem", item)
            val intent = Intent(it.context, DetailsActivity::class.java)
            //Прикрепляем бандл к интенту
            intent.putExtras(bundle)
            //переход на новое activity
            startActivity(it.context, intent, null)
        }
    }
}