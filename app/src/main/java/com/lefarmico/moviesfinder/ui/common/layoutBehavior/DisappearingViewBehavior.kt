package com.lefarmico.moviesfinder.ui.common.layoutBehavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout

class DisappearingViewBehavior(context: Context, attrs: AttributeSet) :
    CoordinatorLayout.Behavior<View>(context, attrs) {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return dependency is AppBarLayout
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        val appBarLayout = dependency as AppBarLayout
        val range = appBarLayout.totalScrollRange
        val factor = -appBarLayout.y / range
        child.alpha = 1 - factor
        return true
    }
}
