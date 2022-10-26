package com.lefarmico.moviesfinder.utils.component.bottomSheet

interface BottomSheetStateListener {

    fun onHide()

    fun onExpand()

    fun onHalfExpand()

    fun onSlide(offset: Float)
}
