package com.lefarmico.moviesfinder.ui.movie

interface BottomSheetStateListener {

    fun onHide()

    fun onExpand()

    fun onHalfExpand()

    fun onSlide(offset: Float)
}
