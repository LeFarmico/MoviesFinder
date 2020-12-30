package com.lefarmico.moviesfinder.adapterDelegates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.adapterDelegates.container.Container
import com.lefarmico.moviesfinder.adapterDelegates.container.ParentModel
import com.lefarmico.moviesfinder.adapterDelegates.item.Item
import com.lefarmico.moviesfinder.adapterDelegates.item.MovieItem
import com.lefarmico.moviesfinder.decorators.TopSpacingItemDecoration
import kotlinx.android.synthetic.main.parent_recycler.view.*

class ContainerDelegateAdapter(
    private val containerItemClickListener: ContainerAdapter.OnContainerClickListener):
    AbsListItemAdapterDelegate<ParentModel, Container, ContainerDelegateAdapter.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
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
        applyItemRecycleViewSetting(holder, item) //принимаем параметры для RV
    }

    private fun applyItemRecycleViewSetting(holder: ViewHolder, item: ParentModel){
        holder.recyclerView.apply {
            (layoutManager as LinearLayoutManager).initialPrefetchItemCount = 4

            adapter = ItemAdapter(object : ItemAdapter.OnItemClickListener{
                override fun click(item: Item) {
                    if(item is MovieItem){
                        containerItemClickListener.click(item)
//                        //Создаем бандл и кладем туда объект с данными фильма
//                        val bundle = Bundle()
//                        //Первым параметром указывается ключ, по которому потом будем искать, вторым сам
//                        //передаваемый объект
//                        bundle.putParcelable("movieItem", item)
//                        val intent = Intent(context, DetailsActivity::class.java)
//                        //Прикрепляем бандл к интенту
//                        intent.putExtras(bundle)
//                        //переход на новое activity
//                        ContextCompat.startActivity(context, intent, null)
                    }
                    else return
                }
            })
            (adapter as ItemAdapter).items = item.items //Передаем items из ContainerAdapter (Очень важно)

            setRecycledViewPool(viewPool)

            layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.starting_posters_animation_layout)
            scheduleLayoutAnimation()
            addItemDecoration(TopSpacingItemDecoration(2))
        }
    }
}