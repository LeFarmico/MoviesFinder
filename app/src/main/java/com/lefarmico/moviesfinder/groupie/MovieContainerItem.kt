package com.lefarmico.moviesfinder.groupie

import com.lefarmico.moviesfinder.R
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item

import kotlinx.android.synthetic.main.parent_recycler.*


class MovieContainerItem(
    private val categoryName: String? = "",
    private val onClick: (title: String) -> Unit,
    private val items: List<Item>
    ) : Item() {

    private var groupAdapter = GroupAdapter<GroupieViewHolder>()
    override fun getLayout() = R.layout.parent_recycler

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.category_name.text = categoryName
        viewHolder.recycler_child.adapter =
            groupAdapter.apply {
                addAll(items)
            }
//        groupAdapter.setOnItemClickListener { item, _ ->
//            if (item is MovieItem) {
////            Toast.makeText(this@MainActivity, "Clicked on movie called: " + item.movieContent.title, Toast.LENGTH_SHORT).show()
//            }
//        }
    }

}