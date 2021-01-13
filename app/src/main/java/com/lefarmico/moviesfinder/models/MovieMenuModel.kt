package com.lefarmico.moviesfinder.models

import com.lefarmico.moviesfinder.ContainerDataFactory
import com.lefarmico.moviesfinder.data.Header
import com.lefarmico.moviesfinder.data.IHeader
import com.lefarmico.moviesfinder.data.Item
import com.lefarmico.moviesfinder.data.ItemsData

class MovieMenuModel() : MenuModel {

    override val itemsGroup = mutableMapOf<IHeader, ItemsData>()
    private val dataFactory = ContainerDataFactory()

    // TODO : возвращять обработать на NPE
    override fun getItem(titleName: String, position: Int): Item =
        itemsGroup[Header(titleName)]?.items?.get(position)!!

    override fun getItemsByTitle(title: String): List<Item> {
        return if (itemsGroup[Header(title)] != null) {
            itemsGroup[Header(title)]?.items!!
        } else {
            emptyList()
        }
    }

    // Обращаться к базе
    // TODO : Создать конструктор
    override fun setItems() {
        val group = mutableMapOf<IHeader, ItemsData>()
        for (i in 0..10) {
            group[Header(dataFactory.getRandomCategory())] =
                ItemsData(dataFactory.getRandomMovies(10))
        }
        itemsGroup.putAll(group)
    }
}
