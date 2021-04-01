package com.lefarmico.moviesfinder.presenters

import android.util.Log
import androidx.annotation.StringRes
import androidx.recyclerview.widget.ConcatAdapter
import com.lefarmico.moviesfinder.adapters.HeaderAdapter
import com.lefarmico.moviesfinder.adapters.ItemsPlaceholderAdapter
import com.lefarmico.moviesfinder.data.Interactor
import com.lefarmico.moviesfinder.fragments.MovieFragment
import com.lefarmico.moviesfinder.models.CategoryModel
import com.lefarmico.moviesfinder.providers.CategoryProvider
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
        interactor.getMoviesFromApi(CategoryProvider.POPULAR_CATEGORY, 1, this)
        interactor.getMoviesFromApi(CategoryProvider.UPCOMING_CATEGORY, 1, this)
        interactor.getMoviesFromApi(CategoryProvider.TOP_RATED_CATEGORY, 1, this)
        interactor.getMoviesFromApi(CategoryProvider.NOW_PLAYING_CATEGORY, 1, this)
    }

    override fun loadCategory(categoryModel: CategoryModel) {
        if (!this::concatAdapter.isInitialized) {
            concatAdapter = ConcatAdapter()
        }
        val headerAdapter = HeaderAdapter().apply {
            addItem(view.requireContext().getString(categoryModel.titleResource))
        }
        concatAdapter.addAdapter(headerAdapter)
        val itemsAdapter = ItemsPlaceholderAdapter(interactor).apply {
            setNestedItemsData(categoryModel.items)
            categoryType = categoryModel.categoryType
        }
        concatAdapter.addAdapter(itemsAdapter)

        view.showCatalog(concatAdapter)
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
                val itemsAdapter = ItemsPlaceholderAdapter(interactor).apply {
                    setNestedItemsData(categoryModels[i].items)
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
