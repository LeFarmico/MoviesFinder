package com.lefarmico.moviesfinder.customViews

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import com.lefarmico.moviesfinder.R
import java.lang.Integer.min

class PersonalRatingView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : View(context, attributeSet) {

    private var viewSize = 0f
    private var cornerSize: Float

    private var redViewColor = Color.parseColor("#F44336")
    private var yellowViewColor = Color.parseColor("#FFC107")
    private var greenViewColor = Color.parseColor("#8BC34A")
    private var deepGreenColor = Color.parseColor("#4CAF50")
    private var defaultColor = Color.parseColor("#BFBFBF")
    private var numbersColor = Color.parseColor("#F1F0F5")

    private var ratingNumber = 0

    init {
        val attributes = context.theme.obtainStyledAttributes(attributeSet, R.styleable.PersonalRatingView, 0, 0)

        try {
            viewSize = attributes.getDimension(R.styleable.PersonalRatingView_view_size, 10f)
            ratingNumber = attributes.getInt(R.styleable.PersonalRatingView_rating_number, 0)
        } finally {
            attributes.recycle()
            cornerSize = viewSize / 3
        }
    }
    private val viewPaint = Paint().apply {
        color = setViewColor(ratingNumber)
        style = Paint.Style.FILL
    }
    private val textPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = numbersColor
        textSize = viewSize * 0.40f
        typeface = Typeface.DEFAULT

        isAntiAlias = true
        textAlign = Paint.Align.CENTER
    }

    private var numberPosX = viewSize / 2
    private var numberPosY = (viewSize / 2) - ((textPaint.descent() + textPaint.ascent()) / 2)

    private fun setViewColor(ratingNumber: Int): Int {
        return when (ratingNumber) {
            in 1..49 -> {
                redViewColor
            }
            in 50..75 -> {
                yellowViewColor
            }
            in 76..89 -> {
                greenViewColor
            }
            in 90..100 -> {
                deepGreenColor
            }
            else -> {
                defaultColor
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawRoundRect(RectF(0.0f, 0.0f, viewSize, viewSize), cornerSize, cornerSize, viewPaint)
        canvas?.drawText(ratingNumber.toString(), numberPosX, numberPosY, textPaint)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val corpWidth = viewSize
        val corpHeight = viewSize

        val width: Int
        val height: Int

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        width = when (widthMode) {
            MeasureSpec.EXACTLY -> widthSize
            MeasureSpec.AT_MOST -> min(widthSize, corpWidth.toInt())
            else -> corpWidth.toInt()
        }
        height = when (heightMode) {
            MeasureSpec.EXACTLY -> heightSize
            // Can't be bigger than...
            MeasureSpec.AT_MOST -> kotlin.math.min(heightSize, corpHeight.toInt())
            // Be whatever you want
            else -> corpHeight.toInt()
        }
        setMeasuredDimension(width, height)
    }
}
