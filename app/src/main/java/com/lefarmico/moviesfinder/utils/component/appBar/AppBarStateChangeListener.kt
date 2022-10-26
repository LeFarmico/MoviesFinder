package com.lefarmico.moviesfinder.utils.component.appBar

import android.os.Parcelable
import com.google.android.material.appbar.AppBarLayout
import kotlinx.parcelize.Parcelize
import kotlin.math.abs

abstract class AppBarStateChangeListener : AppBarLayout.OnOffsetChangedListener {

    @Parcelize
    sealed class State : Parcelable {
        object Idle : State()
        object Expanded : State()
        object Collapsed : State()
    }

    private var mState: State = State.Idle

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        if (appBarLayout == null) return

        if (verticalOffset == 0) {
            if (mState !is State.Expanded) {
                onStateChanged(appBarLayout, State.Expanded)
            }
            mState = State.Expanded
        } else if (abs(verticalOffset) >= appBarLayout.totalScrollRange) {
            if (mState !is State.Collapsed) {
                onStateChanged(appBarLayout, State.Collapsed)
            }
            mState = State.Collapsed
        } else {
            if (mState !is State.Idle) {
                onStateChanged(appBarLayout, State.Idle)
            }
            mState = State.Idle
        }
    }

    abstract fun onStateChanged(appBarLayout: AppBarLayout?, state: State)
}
