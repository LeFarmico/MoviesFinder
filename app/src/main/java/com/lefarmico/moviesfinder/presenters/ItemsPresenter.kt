package com.lefarmico.moviesfinder.presenters

import androidx.recyclerview.widget.ConcatAdapter
import com.lefarmico.moviesfinder.DataFactory
import com.lefarmico.moviesfinder.data.Header
import com.lefarmico.moviesfinder.fargments.MovieFragment
import com.lefarmico.moviesfinder.models.ItemsModelDatabase

class ItemsPresenter(val view: MovieFragment) : MainPresenter {

    // TODO : Создать коллбэки типа onItemWasClicked

    private val modelDatabase: ItemsModelDatabase

    companion object {
        lateinit var instance: ItemsPresenter
    }
    init {
        instance = this
        modelDatabase = ItemsModelDatabase()
    }

    override fun loadData() {
        modelDatabase.loadData()
        val adapters = modelDatabase.getAdapters()
        val headerGenerator = DataFactory()
        for (i in adapters.indices) {
            (view.recyclerView.adapter as ConcatAdapter).apply {
                addAdapter(
                    modelDatabase.addCategoryAdapter(
                        Header(
                            headerGenerator.getRandomCategory()
                        )
                    )
                )
                addAdapter(adapters[i])
            }
        }
    }

    override fun updateData() {
        modelDatabase.updateData()
    }

    override fun errorData() {
        TODO("Not yet implemented")
    }
}
