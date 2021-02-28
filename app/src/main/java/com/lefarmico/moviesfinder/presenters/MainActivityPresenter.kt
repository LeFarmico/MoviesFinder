package com.lefarmico.moviesfinder.presenters

import androidx.fragment.app.Fragment
import com.lefarmico.moviesfinder.activities.MainActivity
import com.lefarmico.moviesfinder.models.Item

interface MainActivityPresenter {
    fun attachView(view: MainActivity)
    fun launchFragment(fragment: Fragment, tag: String)
    fun onMovieClick(item: Item)
    fun onFabClick()
    fun onBottomNavigationMenuClick()
    fun onError()
    fun onLoaded()
    fun onMenuClick(fragment: Fragment, tag: String)
}
