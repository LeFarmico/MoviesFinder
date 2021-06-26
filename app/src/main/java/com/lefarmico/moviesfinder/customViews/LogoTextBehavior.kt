package com.lefarmico.moviesfinder.customViews

import android.view.View
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout

class LogoTextBehavior : CoordinatorLayout.Behavior<TextView>() {

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: TextView,
        dependency: View
    ): Boolean {

        val expandedPercentageFactor = dependency.y

        child.scaleX = expandedPercentageFactor
        child.scaleY = expandedPercentageFactor

        return true
    }

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: TextView,
        dependency: View
    ): Boolean {
        return dependency is AppBarLayout
    }
}
