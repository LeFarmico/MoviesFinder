package com.lefarmico.moviesfinder.utils.component.bottomSheet

import android.view.View
import androidx.lifecycle.Lifecycle

interface BottomSheetBehaviourHandler {

    // Binds Bottom Sheet have to Implement BottomSheetBehaviour
    fun registerBottomSheetHandler(bottomSheet: View, lifecycle: Lifecycle, listener: BottomSheetStateListener?)

    fun expandBottomSheet()

    fun halfExpandBottomSheet()

    fun hideBottomSheet()
}
