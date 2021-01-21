package com.lefarmico.moviesfinder.models

import com.lefarmico.moviesfinder.DataFactory
import com.lefarmico.moviesfinder.adapters.HeaderAdapter
import com.lefarmico.moviesfinder.adapters.ItemsPlaceholderAdapter
import com.lefarmico.moviesfinder.data.IHeader
import com.lefarmico.moviesfinder.data.Item
import com.lefarmico.moviesfinder.data.ItemsData

class ItemsDatabaseModel : ModelDataBase {

    companion object {
        lateinit var instance: ItemsDatabaseModel
    }
    init {
        instance = this
    }

    private var nextId = 1
    override val models = mutableMapOf<Int, ModelItems>()
    override val modelIds = mutableListOf<Int>()

    @Synchronized
    fun saveItemsModel(model: ModelItems) {
        val id = nextId++
        modelIds.add(id)
        model.id = id
        models[id] = model
    }

    override fun loadData() {
        setItemsModels(itemsDataGenerator(10))
    }
    override fun updateData() {
        instance = ItemsDatabaseModel()
        setItemsModels(itemsDataGenerator(10))
    }

    override fun updateModelData(modelId: Int) {
        TODO("Not yet implemented")
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

    override fun getAdapters(): List<ItemsPlaceholderAdapter> {
        val ids = modelIds
        val adapters = mutableListOf<ItemsPlaceholderAdapter>()
        for (i in ids.indices) {
            adapters.add(
                getItemsModel(ids[i])?.adapter!!
            )
        }
        return adapters
    }

    override fun getItemsModel(id: Int): ModelItems? = models[id]
    override fun getSingleInstanceItems(): List<Item> {
        TODO("Not yet implemented")
    }

    fun getAllItems(): List<ItemsData> {
        val list = mutableListOf<ItemsData>()
        for (i in modelIds.indices) {
            list.add(models[modelIds[i]]?.itemsData!!)
        }
        return list
    }
}
