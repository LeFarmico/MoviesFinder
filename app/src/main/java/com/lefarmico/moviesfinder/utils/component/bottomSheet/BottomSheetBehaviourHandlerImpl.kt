package com.lefarmico.moviesfinder.utils.component.bottomSheet

import android.view.View
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.bottomsheet.BottomSheetBehavior

class BottomSheetBehaviourHandlerImpl : BottomSheetBehaviourHandler, DefaultLifecycleObserver {

    private var bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            invokeStateChangeListenerAction(newState)
        }
        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            stateListener?.onSlide(slideOffset)
        }
    }
    private var bottomSheetBehavior: BottomSheetBehavior<*>? = null

    private var isInitialState = true

    private var stateListener: BottomSheetStateListener? = null

    override fun registerBottomSheetHandler(
        bottomSheet: View,
        lifecycle: Lifecycle,
        listener: BottomSheetStateListener?
    ) {
        lifecycle.addObserver(this)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        stateListener = listener
    }

    override fun expandBottomSheet() {
        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
        doIfInitialState { stateListener?.onExpand() }
    }

    override fun halfExpandBottomSheet() {
        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        doIfInitialState { stateListener?.onHalfExpand() }
    }

    override fun hideBottomSheet() {
        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
        doIfInitialState { stateListener?.onHide() }
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        bottomSheetBehavior?.addBottomSheetCallback(bottomSheetCallback)
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        bottomSheetBehavior?.removeBottomSheetCallback(bottomSheetCallback)
    }

    private fun doIfInitialState(action: () -> Unit) {
        if (isInitialState) {
            action.invoke()
            isInitialState = false
        }
    }

    private fun invokeStateChangeListenerAction(state: Int) {
        when (state) {
            BottomSheetBehavior.STATE_HIDDEN -> stateListener?.onHide()
            BottomSheetBehavior.STATE_EXPANDED -> stateListener?.onExpand()
            BottomSheetBehavior.STATE_HALF_EXPANDED -> stateListener?.onHalfExpand()
            else -> {}
        }
    }
}
