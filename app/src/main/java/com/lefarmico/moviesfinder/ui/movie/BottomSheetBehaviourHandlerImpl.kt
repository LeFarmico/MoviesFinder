package com.lefarmico.moviesfinder.ui.movie

import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior

class BottomSheetBehaviourHandlerImpl : BottomSheetBehaviourHandler {

    private val behavior by lazy {
        try {
            (bottomSheet.layoutParams as CoordinatorLayout.LayoutParams).behavior
                as BottomSheetBehavior<*>
        } catch (e: NullPointerException) {
            throw RuntimeException("BottomSheet view is not bind")
        } catch (e: TypeCastException) {
            throw RuntimeException("Bind view is not provide bottom sheet behaviour")
        }
    }

    private var isInitialState = true

    private var stateListener: BottomSheetStateListener? = null
    override fun getCurrentState(): Int = behavior.state

    private lateinit var bottomSheet: View

    override fun bindBS(bottomSheet: View) {
        this.bottomSheet = bottomSheet
        BottomSheetBehavior.from(bottomSheet).addBottomSheetCallback(
            object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    setStateParameters(newState)
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    stateListener?.onSlide(slideOffset)
                }
            }
        )
    }

    override fun bindBSStateListener(bottomSheetStateListener: BottomSheetStateListener) {
        stateListener = bottomSheetStateListener
    }

    override fun expandBS() {
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        ifInitialSetStateParameters()
    }

    override fun halfExpandBS() {
        behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        ifInitialSetStateParameters()
    }

    override fun hideBS() {
        behavior.state = BottomSheetBehavior.STATE_HIDDEN
        ifInitialSetStateParameters()
    }

    private fun ifInitialSetStateParameters() {
        if (isInitialState) {
            setStateParameters(getCurrentState())
            isInitialState = false
        }
    }

    private fun setStateParameters(state: Int) {
        when (state) {
            BottomSheetBehavior.STATE_HIDDEN -> stateListener?.onHide()
            BottomSheetBehavior.STATE_EXPANDED -> stateListener?.onExpand()
            BottomSheetBehavior.STATE_HALF_EXPANDED -> stateListener?.onHalfExpand()
            else -> {}
        }
    }
}
