package com.lefarmico.moviesfinder.presenters

import com.lefarmico.moviesfinder.activities.MainActivity
import com.lefarmico.moviesfinder.data.Interactor
import com.lefarmico.moviesfinder.models.ItemHeader
import com.lefarmico.moviesfinder.models.MovieItem
import com.lefarmico.moviesfinder.view.MainActivityView
import javax.inject.Inject

class MainActivityPresenterImpl @Inject constructor() : MainActivityPresenter {

    private lateinit var view: MainActivityView
    lateinit var interactor: Interactor

    override fun attachView(view: MainActivity) {
        this.view = view
        this.interactor = view.interactor
    }

    override fun onItemClick(itemHeader: ItemHeader) {
        interactor.getMovieDetailsFromApi(itemHeader, itemHeader.id, this)
    }

    override fun showItemDetails(movieItem: MovieItem) {
        view.launchItemDetails(movieItem)
    }

    override fun onFabClick() {
        view.launchFabMenu()
    }

    override fun showError(textResource: Int) {
        TODO("Not yet implemented")
    }

    override fun onLoaded() {
        TODO("Not yet implemented")
    }
}
