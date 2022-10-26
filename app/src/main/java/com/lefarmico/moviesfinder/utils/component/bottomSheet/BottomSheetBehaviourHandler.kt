package com.lefarmico.moviesfinder.utils.component.bottomSheet

import android.view.View

interface BottomSheetBehaviourHandler {

    // Binds Bottom Sheet have to Implement BottomSheetBehaviour
    fun bindBS(bottomSheet: View)

    fun bindBSStateListener(bottomSheetStateListener: BottomSheetStateListener)

    fun expandBS()

    fun halfExpandBS()

    fun hideBS()

    // Can be called only after bindBS() function
    fun getCurrentState(): Int
}
