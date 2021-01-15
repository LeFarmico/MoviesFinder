package com.lefarmico.moviesfinder.presenters

import androidx.recyclerview.widget.ConcatAdapter
import com.lefarmico.moviesfinder.ContainerDataFactory
import com.lefarmico.moviesfinder.adapters.HeaderAdapter
import com.lefarmico.moviesfinder.adapters.ItemsPlaceholderAdapter
import com.lefarmico.moviesfinder.data.Header
import com.lefarmico.moviesfinder.data.IHeader
import com.lefarmico.moviesfinder.data.ItemsData
import com.lefarmico.moviesfinder.models.ItemsModelDatabase
import com.lefarmico.moviesfinder.fargments.MovieFragment
import com.lefarmico.moviesfinder.models.ItemsModel
import com.lefarmico.moviesfinder.models.ItemsModelInterface

class ItemsPresenter(val view: MovieFragment) {

    // TODO : Создать коллбэки типа onItemWasClicked

    private val modelDatabase: ItemsModelDatabase

    companion object {
        lateinit var instance: ItemsPresenter
    }
    init {
        instance = this
        modelDatabase = ItemsModelDatabase(this)
    }

    fun setItemsModel(model: ItemsModelInterface) {
        modelDatabase.saveItemsModel(model)
    }

    fun getItems(modelId: Int) : ItemsData? =
        modelDatabase.getItemsModel(modelId)?.itemsData

    fun getAdapterById(id: Int) : ItemsPlaceholderAdapter =
        modelDatabase.getItemsModel(id)?.adapter!!

    // Вызывается из Fragment или Activity
    fun createItems(itemsData: ItemsData) {
        val model = ItemsModel(itemsData)
        model.setItems(itemsData)
        modelDatabase.saveItemsModel(model)
    }

    // Избавиться
    private fun getAdapters() : List<ItemsPlaceholderAdapter>{
        val ids = modelDatabase.getIdList()
        val adapters = mutableListOf<ItemsPlaceholderAdapter>()
        for (i in ids.indices) {
            adapters.add(
                modelDatabase.getItemsModel(ids[i])?.adapter!!
            )
        }
        return adapters
    }

    fun showAdapters() {
        setItemsModels(
            itemsDataGenerator(10)
        )
        setAdapters()
    }

    // TODO : Вынести все в отдельный класс factory
    private fun setItemsModels(itemsDataList: List<ItemsData>){
        for (i in itemsDataList.indices) {
            setItemsModel(ItemsModel(itemsDataList[i]))
        }
    }
    private fun itemsDataGenerator(count: Int) : List<ItemsData> {
        val itemsDataList = mutableListOf<ItemsData>()
        val factory = ContainerDataFactory()
        for (i in 0..count){
            val data = ItemsData(factory.getRandomMovies(11))
            itemsDataList.add(data)
        }
        return itemsDataList
    }
    private fun setAdapters(){
        val adapters = getAdapters()
        for (i in adapters.indices) {
            (view.recyclerView.adapter as ConcatAdapter).apply {
                addAdapter(
                    addCategoryAdapter(
                        Header("Hello")
                    )
                )
                addAdapter(adapters[i])
            }
        }
    }
    private fun addCategoryAdapter(title: IHeader) : HeaderAdapter {
        val adapter = HeaderAdapter()
        adapter.addItem(title.title)
        return adapter
    }
}