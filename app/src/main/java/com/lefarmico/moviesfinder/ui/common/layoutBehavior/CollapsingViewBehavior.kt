package com.lefarmico.moviesfinder.ui.common.layoutBehavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout

class CollapsingViewBehavior(context: Context, attrs: AttributeSet) :
    CoordinatorLayout.Behavior<View>(context, attrs) {

    private var mView: Array<Int>? = null

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
        setup(child)

        val appBarLayout = dependency as AppBarLayout

        val range = appBarLayout.totalScrollRange
        val factor = -appBarLayout.y / range

        // TODO: Fix left and top of the layout
        val left = mView!![X] + (factor * (mView!![X])).toInt()
        val top = mView!![Y] - (factor * (mView!![Y])).toInt()
        val width = mView!![WIDTH] - (factor * (mView!![WIDTH])).toInt()
        val height = mView!![HEIGHT] - (factor * (mView!![HEIGHT])).toInt()

        val lp = child.layoutParams as CoordinatorLayout.LayoutParams
        lp.width = width
        lp.height = height
        child.layoutParams = lp
        child.x = left.toFloat()
//        child.y = top.toFloat()

        return true
    }

    private fun setup(child: View) {
        if (mView != null) return

        mView = Array(4) {
            when (it) {
                X -> child.x.toInt()
                Y -> child.y.toInt()
                WIDTH -> child.width
                HEIGHT -> child.height
                else -> { it }
            }
        }
    }

    companion object {
        const val X = 0
        const val Y = 1
        const val WIDTH = 2
        const val HEIGHT = 3
    }
}
