package com.lefarmico.moviesfinder.decorators

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.DecelerateInterpolator

class FabMenuAnimator(
    private val menu1: View?,
    private val menu2: View?,
    private val menu3: View?
) {

    private var valueAnimator: ValueAnimator? = null

    private val propertyTranslation = PropertyValuesHolder.ofFloat("trans", 0f, -200f)
    private val propertyAlpha = PropertyValuesHolder.ofFloat("alpha", 0f, 1f)

    private var isShown: Boolean? = null

    fun setAnimator(duration: Long) {
        valueAnimator = ValueAnimator().apply {
            setValues(propertyTranslation, propertyAlpha)
            this.duration = duration
            interpolator = DecelerateInterpolator()
        }
    }
    fun onShowMenu() {
        valueAnimator?.addUpdateListener {
            menu1?.translationX = it.getAnimatedValue("trans") as Float
            menu2?.translationX = it.getAnimatedValue("trans") as Float
            menu2?.translationY = it.getAnimatedValue("trans") as Float
            menu3?.translationY = it.getAnimatedValue("trans") as Float

            menu1?.alpha = it.getAnimatedValue("alpha") as Float
            menu2?.alpha = it.getAnimatedValue("alpha") as Float
            menu3?.alpha = it.getAnimatedValue("alpha") as Float
        }
        valueAnimator?.start()
        isShown = true
    }

    fun onHideMenu() {
        valueAnimator?.addUpdateListener {
            menu1?.translationX = it.getAnimatedValue("trans") as Float
            menu2?.translationX = it.getAnimatedValue("trans") as Float
            menu2?.translationY = it.getAnimatedValue("trans") as Float
            menu3?.translationY = it.getAnimatedValue("trans") as Float

            menu1?.alpha = it.getAnimatedValue("alpha") as Float
            menu2?.alpha = it.getAnimatedValue("alpha") as Float
            menu3?.alpha = it.getAnimatedValue("alpha") as Float
        }
        valueAnimator?.reverse()
        isShown = false
    }

    fun onMenuClick() {
        if (isShown!!) {
            onShowMenu()
        } else {
            onHideMenu()
        }
    }
}
