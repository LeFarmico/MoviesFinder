package com.lefarmico.moviesfinder.animations

import android.animation.ValueAnimator
import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.animation.doOnEnd

class MaxLineAnimator(val textView: TextView, val duration: Long, val collapseAnimation: Boolean, val collapsedLines: Int, context: Context) {

    var valueAnimator: ValueAnimator
    val expectedWidthOfTextView = context.resources.displayMetrics.widthPixels
    val originalMaxLines = textView.maxLines

    var measuredLineCount = textView.lineCount
    var measuredTargetHeight = textView.measuredHeight

    var layoutParams: ViewGroup.LayoutParams

    init {
        if (originalMaxLines < 0 || originalMaxLines == Integer.MAX_VALUE) {
            Log.d("AppLog", "already unbounded textView maxLines")
            textView.maxLines = collapsedLines
            textView.measure(
                View.MeasureSpec.makeMeasureSpec(expectedWidthOfTextView, View.MeasureSpec.AT_MOST),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            )
            measuredLineCount = textView.lineCount
            measuredTargetHeight = textView.measuredHeight
            textView.maxLines = originalMaxLines
        } else {
            textView.maxLines = Integer.MAX_VALUE
            textView.measure(
                View.MeasureSpec.makeMeasureSpec(expectedWidthOfTextView, View.MeasureSpec.AT_MOST),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            )
            measuredLineCount = textView.lineCount
            measuredTargetHeight = textView.measuredHeight
            Log.d("AppLog", "lines:$measuredLineCount/$originalMaxLines")
            textView.maxLines = originalMaxLines
        }
        layoutParams = textView.layoutParams
        valueAnimator = ValueAnimator
            .ofInt(textView.height, measuredTargetHeight)
            .setDuration(duration)
        valueAnimator.addUpdateListener { valueAnimator ->
            val value: Int = valueAnimator.animatedValue as Int
            layoutParams.height = value
            textView.requestLayout()
        }
    }
    fun start() {
        if (measuredLineCount <= originalMaxLines && collapseAnimation) {
            Log.d("AppLog", "fit in original maxLines")
            collapse()
        } else {
            Log.d("AppLog", "exceeded original maxLines")
            open()
        }
    }
    fun open() {
        textView.maxLines = Integer.MAX_VALUE
        valueAnimator.start()
        layoutParams.height = textView.height
    }
    fun collapse() {
        if (measuredLineCount <= originalMaxLines) {
            Log.d("AppLog", "fit in original maxLines")
            valueAnimator.start()
            layoutParams.height = textView.height
            valueAnimator.doOnEnd {
                textView.maxLines = collapsedLines
            }
        }
    }
}
