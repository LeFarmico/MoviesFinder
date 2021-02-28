package com.lefarmico.moviesfinder.presenters

import androidx.fragment.app.Fragment
import com.lefarmico.moviesfinder.App
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.activities.MainActivity
import com.lefarmico.moviesfinder.data.Interactor
import com.lefarmico.moviesfinder.models.Item
import com.lefarmico.moviesfinder.view.MainView
import javax.inject.Inject

class MainActivityPresenterImpl @Inject constructor() : MainActivityPresenter {

    private lateinit var view: MainView
    private var interactor: Interactor = App.instance.interactor

//    val movieFragment = MovieFragment()
//    val seriesFragment = SeriesFragment()
//    val favoritesFragment = FavoritesFragment()

    override fun attachView(view: MainActivity) {
        this.view = view
    }

    override fun launchFragment(fragment: Fragment, tag: String) {
        view.launchFragment(fragment, tag)
    }

    private fun isFragmentExist(tag: String): Fragment? = view.fragmentManager.findFragmentByTag(tag)

    private fun launchPrimaryFragment() {
        view.fragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.nav_host_fragment, view.fragmentManager.primaryNavigationFragment!!)
            .commit()
    }

    override fun onMovieClick(item: Item) {
        view.launchItemDetails(item)
    }

    override fun onFabClick() {
        view.launchFabMenu()
    }

    override fun onBottomNavigationMenuClick() {
        TODO("Not yet implemented")
    }

    override fun onError() {
        TODO("Not yet implemented")
    }

    override fun onLoaded() {
        TODO("Not yet implemented")
    }

    override fun onMenuClick(fragment: Fragment, tag: String) {
        view.launchFragment(fragment, tag)
    }
}
