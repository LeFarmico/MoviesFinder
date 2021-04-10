package com.lefarmico.moviesfinder.presenters

import android.util.Log
import androidx.annotation.StringRes
import androidx.recyclerview.widget.ConcatAdapter
import com.lefarmico.moviesfinder.adapters.HeaderAdapter
import com.lefarmico.moviesfinder.adapters.ItemsPlaceholderAdapter
import com.lefarmico.moviesfinder.data.Interactor
import com.lefarmico.moviesfinder.data.entity.appEntity.Category
import com.lefarmico.moviesfinder.fragments.MovieFragment
import javax.inject.Inject

class MovieFragmentPresenter @Inject constructor() : FragmentMenuPresenter {

    private lateinit var concatAdapter: ConcatAdapter
    private lateinit var view: MovieFragment
    lateinit var interactor: Interactor

    init {
        Log.d("Presenter", this.hashCode().toString())
    }

    fun attachView(view: MovieFragment) {
        this.view = view
        this.interactor = view.interactor
    }

    // provider создается не во view
    fun loadData() {
//        interactor.getMovieCategoryFromApi(CategoryProvider.POPULAR_CATEGORY, 1, this)
//        interactor.getMovieCategoryFromApi(CategoryProvider.UPCOMING_CATEGORY, 1, this)
//        interactor.getMovieCategoryFromApi(CategoryProvider.TOP_RATED_CATEGORY, 1, this)
//        interactor.getMovieCategoryFromApi(CategoryProvider.NOW_PLAYING_CATEGORY, 1, this)
    }

    override fun loadCategory(category: Category) {
        if (!this::concatAdapter.isInitialized) {
            concatAdapter = ConcatAdapter()
        }
        val headerAdapter = HeaderAdapter().apply {
            addItem(view.requireContext().getString(category.titleResource))
        }
        concatAdapter.addAdapter(headerAdapter)
        val itemsAdapter = ItemsPlaceholderAdapter(interactor).apply {
            setNestedItemsData(category.itemsList)
            categoryType = category.categoryType
        }
        concatAdapter.addAdapter(itemsAdapter)

        view.showCatalog(concatAdapter)
    }
    override fun categoriesLoaded(categories: List<Category>) {
        // тут ли создавать адаптеры?
        if (!this::concatAdapter.isInitialized) {
            concatAdapter = ConcatAdapter()
            for (i in categories.indices) {
                val headerAdapter = HeaderAdapter().apply {
                    addItem(
                        view.requireContext().getString(categories[i].titleResource)
                    )
                }
                concatAdapter.addAdapter(headerAdapter)
                val itemsAdapter = ItemsPlaceholderAdapter(interactor).apply {
                    setNestedItemsData(categories[i].itemsList)
                }
                concatAdapter.addAdapter(itemsAdapter)
            }
        }
        view.showCatalog(concatAdapter)
    }

    override fun showError(@StringRes textResource: Int) {
//        TODO("Not yet implemented")
    }
}
