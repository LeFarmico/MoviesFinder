package com.lefarmico.moviesfinder.presenters

import androidx.recyclerview.widget.ConcatAdapter
import com.lefarmico.moviesfinder.DataFactory
import com.lefarmico.moviesfinder.data.Header
import com.lefarmico.moviesfinder.fragments.MovieFragment
import com.lefarmico.moviesfinder.models.ItemsDatabaseModel
import javax.inject.Inject

class ItemsPresenter() : MainPresenter {

    // TODO : Создать коллбэки типа onItemWasClicked

    private lateinit var databaseModel: ItemsDatabaseModel
    private lateinit var view: MovieFragment

    companion object {
        lateinit var instance: ItemsPresenter
    }
    init {
        instance = this
//        databaseModel = view.databaseModel
    }

    override fun loadData() {
        databaseModel.loadData()
        val adapters = databaseModel.getAdapters()
        val headerGenerator = DataFactory()
        for (i in adapters.indices) {
            (view.recyclerView.adapter as ConcatAdapter).apply {
                addAdapter(
                    databaseModel.addCategoryAdapter(
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
        databaseModel.updateData()
    }

    override fun errorData() {
        TODO("Not yet implemented")
    }

    fun getAllData() {
        databaseModel.getAllItems()
    }
    fun setView(view: MovieFragment) {
        this.view = view
    }
    fun setModel(model: ItemsDatabaseModel) {
        databaseModel = model
    }
}
