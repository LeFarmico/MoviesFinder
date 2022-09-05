package com.lefarmico.moviesfinder.ui.home

import com.lefarmico.moviesfinder.data.entity.MenuItem

data class HomeState(
    val isLoading: Boolean = false,
    val menuItemList: List<MenuItem> = emptyList()
)
