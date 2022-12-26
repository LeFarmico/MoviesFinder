package com.lefarmico.moviesfinder.ui.base

import androidx.annotation.StringRes

data class ErrorViewState(
    @StringRes val title: Int,
    @StringRes val description: Int,
    @StringRes val buttonDescription: Int,
)
