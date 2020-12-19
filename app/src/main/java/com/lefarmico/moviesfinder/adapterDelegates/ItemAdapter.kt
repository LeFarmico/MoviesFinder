package com.lefarmico.moviesfinder.adapterDelegates

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.lefarmico.moviesfinder.DetailsActivity
import com.lefarmico.moviesfinder.adapterDelegates.item.Item
import com.lefarmico.moviesfinder.adapterDelegates.item.MovieItem

class ItemAdapter(context: Context): ListDelegationAdapter<List<Item>>() {
    //Создаем delegate для Ячеек Родительского адаптера
    init {
        delegatesManager.addDelegate(ItemDelegateAdapter(object : ItemDelegateAdapter.OnMovieItemClickListener{
            override fun click(movie: MovieItem) {
                //Создаем бандл и кладем туда объект с данными фильма
                val bundle = Bundle()
                //Первым параметром указывается ключ, по которому потом будем искать, вторым сам
                //передаваемый объект
                bundle.putParcelable("movieItem", movie)
                val intent = Intent(context, DetailsActivity::class.java)
                //Прикрепляем бандл к интенту
                intent.putExtras(bundle)
                //переход на новое activity
                ContextCompat.startActivity(context, intent, null)
            }
        })) //movieDelegateAdaptor
    }

    override fun setItems(items: List<Item>?) {
        super.setItems(items)
        notifyDataSetChanged()
    }
}