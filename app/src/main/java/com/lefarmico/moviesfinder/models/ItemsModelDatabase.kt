package com.lefarmico.moviesfinder.models

import com.lefarmico.moviesfinder.DataFactory
import com.lefarmico.moviesfinder.adapters.HeaderAdapter
import com.lefarmico.moviesfinder.adapters.ItemsPlaceholderAdapter
import com.lefarmico.moviesfinder.data.IHeader
import com.lefarmico.moviesfinder.data.ItemsData

class ItemsModelDatabase() {

    companion object {
        lateinit var instance: ItemsModelDatabase
    }
    init {
        instance = this
    }

    private var nextId = 1
    private val itemsModels = mutableMapOf<Int, ItemsModelInterface>()
    private val idList = mutableListOf<Int>()

    @Synchronized
    fun saveItemsModel(itemsModel: ItemsModelInterface) {
        val id = nextId++
        idList.add(id)
        itemsModel.id = id
        itemsModels[id] = itemsModel
    }

    fun loadData() {
        setItemsModels(itemsDataGenerator(10))
    }
    fun updateData() {
        instance = ItemsModelDatabase()
        setItemsModels(itemsDataGenerator(10))
    }

    private fun setItemsModels(itemsDataList: List<ItemsData>) {
        for (i in itemsDataList.indices) {
            saveItemsModel(ItemsModel(itemsDataList[i]))
        }
    }

    private fun itemsDataGenerator(count: Int): List<ItemsData> {
        val itemsDataList = mutableListOf<ItemsData>()
        val factory = DataFactory()
        for (i in 0..count) {
            val data = ItemsData(factory.getRandomMovies(11))
            itemsDataList.add(data)
        }
        return itemsDataList
    }

    fun addCategoryAdapter(title: IHeader): HeaderAdapter {
        val adapter = HeaderAdapter()
        adapter.addItem(title.title)
        return adapter
    }

    fun getAdapters(): List<ItemsPlaceholderAdapter> {
        val ids = getIdList()
        val adapters = mutableListOf<ItemsPlaceholderAdapter>()
        for (i in ids.indices) {
            adapters.add(
                getItemsModel(ids[i])?.adapter!!
            )
        }
        return adapters
    }

    private fun getIdList(): List<Int> = idList

    private fun getItemsModel(id: Int): ItemsModelInterface? = itemsModels[id]

    fun getItems(modelId: Int): ItemsData? =
        getItemsModel(modelId)?.itemsData

    fun getAllItems(): List<ItemsData> {
        val list = mutableListOf<ItemsData>()
        for (i in idList.indices) {
            list.add(itemsModels[idList[i]]?.getItems()!!)
        }
        return list
    }
//    fun getAdapterById(id: Int): ItemsPlaceholderAdapter =
//        getItemsModel(id)?.adapter!!
}
