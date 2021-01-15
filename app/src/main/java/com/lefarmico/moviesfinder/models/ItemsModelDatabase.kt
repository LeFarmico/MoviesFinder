package com.lefarmico.moviesfinder.models

import com.lefarmico.moviesfinder.adapters.ItemsPlaceholderAdapter
import com.lefarmico.moviesfinder.presenters.ItemsPresenter

class ItemsModelDatabase(private val presenter: ItemsPresenter) {

    companion object {
        // TODO : Тут может что-то работать не так
        lateinit var instance: ItemsModelDatabase
    }
    init {
        instance = this
    }

    private var nextId = 1
    private val itemsModels = mutableMapOf<Int, ItemsModelInterface>()
    private val idList = mutableListOf<Int>()


    fun getItemsModel(id: Int): ItemsModelInterface? = itemsModels[id]

    @Synchronized
    fun saveItemsModel(itemsModel: ItemsModelInterface) {
        val id = nextId++
        idList.add(id)
        itemsModel.id = id
        itemsModels[id] = itemsModel
    }
    fun getIdList() : List<Int> = idList

}
