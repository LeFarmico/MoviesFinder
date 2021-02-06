package com.lefarmico.moviesfinder.presenters

import android.util.Log
import androidx.recyclerview.widget.ConcatAdapter
import com.lefarmico.moviesfinder.adapters.HeaderAdapter
import com.lefarmico.moviesfinder.adapters.ItemsPlaceholderAdapter
import com.lefarmico.moviesfinder.models.Item
import com.lefarmico.moviesfinder.fragments.MovieFragment
import com.lefarmico.moviesfinder.models.CategoryModel
import com.lefarmico.moviesfinder.providers.MovieItemsProvider
import javax.inject.Inject

class MoviePresenter @Inject constructor() : MainMenuPresenter {

    // TODO : Создать коллбэки типа onItemWasClicked
    private lateinit var concatAdapter: ConcatAdapter
    private lateinit var view: MovieFragment
    private lateinit var provider: MovieItemsProvider

    init {
        Log.d("Presenter", this.hashCode().toString())
    }

    fun attachView(view: MovieFragment) {
        this.view = view
    }

    // provider создается не во view
    fun loadData() {
        provider = view.itemsProvider
        provider.loadTestCategories()
    }

    override fun categoriesLoaded(categoryModels: List<CategoryModel>) {
        // тут ли создавать адаптеры?
        if (!this::concatAdapter.isInitialized) {
            concatAdapter = ConcatAdapter()
            for (i in categoryModels.indices) {
                val headerAdapter = HeaderAdapter().apply {
                    addItem(
                        view.requireContext().getString(categoryModels[i].titleResource)
                    )
                }
                concatAdapter.addAdapter(headerAdapter)
                val itemsAdapter = ItemsPlaceholderAdapter().apply {
                    setNestedItemsData(categoryModels[i].items)
                }
                concatAdapter.addAdapter(itemsAdapter)
            }
        }
        view.showCatalog(concatAdapter)
    }

    override fun showError(textResource: Int) {
        TODO("Not yet implemented")
    }

    override fun categoryClicked(category: CategoryModel) {
        TODO("Not yet implemented")
    }

    override fun onItemClicked(item: Item) {
        TODO("Not yet implemented")
    }
}
