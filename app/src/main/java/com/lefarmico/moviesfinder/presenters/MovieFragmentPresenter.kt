package com.lefarmico.moviesfinder.presenters

import android.util.Log
import androidx.annotation.StringRes
import androidx.recyclerview.widget.ConcatAdapter
import com.lefarmico.moviesfinder.App
import com.lefarmico.moviesfinder.adapters.HeaderAdapter
import com.lefarmico.moviesfinder.adapters.ItemsPlaceholderAdapter
import com.lefarmico.moviesfinder.data.Interactor
import com.lefarmico.moviesfinder.fragments.MovieFragment
import com.lefarmico.moviesfinder.models.CategoryModel
import com.lefarmico.moviesfinder.models.ItemHeader
import javax.inject.Inject

class MovieFragmentPresenter @Inject constructor() : FragmentMenuPresenter {

    // TODO : Создать коллбэки типа onItemWasClicked
    private lateinit var concatAdapter: ConcatAdapter
    private lateinit var view: MovieFragment
    private var interactor: Interactor = App.instance.interactor

    init {
        Log.d("Presenter", this.hashCode().toString())
    }

    fun attachView(view: MovieFragment) {
        this.view = view
    }

    // provider создается не во view
    fun loadData() {
        interactor.getPopularMovieFromApi(1, this)
        interactor.getUpcomingMovieFromApi(1, this)
        interactor.getNowPlayingMovieFromApi(1, this)
        interactor.getTopRatedMovieFromApi(1, this)
    }

    override fun loadCategory(categoryModel: CategoryModel) {
        if (!this::concatAdapter.isInitialized) {
            concatAdapter = ConcatAdapter()
        }
        val headerAdapter = HeaderAdapter().apply {
            addItem(view.requireContext().getString(categoryModel.titleResource))
        }
        concatAdapter.addAdapter(headerAdapter)
        val itemsAdapter = ItemsPlaceholderAdapter().apply {
            setNestedItemsData(categoryModel.items)
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
                val itemsAdapter = ItemsPlaceholderAdapter().apply {
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

    override fun categoryClicked(category: CategoryModel) {
//        TODO("Not yet implemented")
    }

    override fun onItemClicked(itemHeader: ItemHeader) {
//        TODO("Not yet implemented")
    }
}
