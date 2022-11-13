package com.lefarmico.moviesfinder.ui.base

import androidx.annotation.StringRes

data class ErrorViewState(
    @StringRes val errorTitle: Int,
    @StringRes val errorDescription: Int,
    @StringRes val errorButtonDescription: Int,
)
