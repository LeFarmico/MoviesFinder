package com.lefarmico.moviesfinder.presenters

import androidx.fragment.app.Fragment
import com.lefarmico.moviesfinder.activities.MainActivity
import com.lefarmico.moviesfinder.data.Interactor
import com.lefarmico.moviesfinder.fragments.FavoritesFragment
import com.lefarmico.moviesfinder.fragments.MovieFragment
import com.lefarmico.moviesfinder.fragments.SeriesFragment
import com.lefarmico.moviesfinder.models.ItemHeader
import com.lefarmico.moviesfinder.models.MovieItem
import com.lefarmico.moviesfinder.view.MainActivityView
import javax.inject.Inject

class MainActivityPresenterImpl @Inject constructor() : MainActivityPresenter {

    private lateinit var view: MainActivityView
    lateinit var interactor: Interactor

    override val fragmentsMap: MutableMap<String, Fragment> = mutableMapOf(
        Pair("MovieFragment", MovieFragment()),
        Pair("SeriesFragment", SeriesFragment()),
        Pair("FavoritesFragment", FavoritesFragment()),
    )

    override fun attachView(view: MainActivity) {
        this.view = view
        this.interactor = view.interactor
    }

    override fun launchFragment(fragment: Fragment, tag: String) {
        view.launchFragment(fragment, tag)
    }

    override fun launchFragments() {
        fragmentsMap.forEach { (tag, fragment) ->
            view.launchFragment(tag = tag, fragment = fragment)
        }
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

    override fun showFragment(fragment: Fragment) {
        view.showFragment(fragment)
    }
}
