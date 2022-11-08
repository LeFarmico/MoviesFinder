package com.lefarmico.moviesfinder.ui.home

import androidx.annotation.StringRes
import com.lefarmico.moviesfinder.data.entity.MenuItem

data class HomeState(
    val isLoading: Boolean = false,
    val error: Error? = null,
    val menuItemList: List<MenuItem> = emptyList()
)

data class Error(
    @StringRes val errorTitle: Int,
    @StringRes val errorDescription: Int,
    @StringRes val errorButtonDescription: Int,
)
