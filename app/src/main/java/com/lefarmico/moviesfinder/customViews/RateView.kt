package com.lefarmico.moviesfinder.customViews

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import com.lefarmico.moviesfinder.R
import kotlin.math.min

class RateView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : View(context, attributeSet) {
    data class ButtonCoordinates(val cx: Float, val cy: Float)

    private var valueAnimator: ValueAnimator? = null

    private var buttonSize = 0f
    private var numberSize = 0f
    private var buttonSpace = 20f
    private var defButtonColor = Color.parseColor("#E0FFFF")
    private var pushedButtonColor = Color.parseColor("#F0E82D")
    private var buttonsCount: Int = 0
    private var isButtonsHorizontal = true

    var abPosX = 0f
    var abPosY = 0f
    var abRadius = 50f

    init {
        val attributes = context.theme.obtainStyledAttributes(attributeSet, R.styleable.RateView, 0, 0)

        try {
            buttonSize = attributes.getFloat(R.styleable.RateView_button_size, 0f)
            defButtonColor = attributes.getColor(R.styleable.RateView_default_color, Color.WHITE)
            pushedButtonColor = attributes.getColor(R.styleable.RateView_pushed_color, Color.YELLOW)
            buttonsCount = attributes.getInt(R.styleable.RateView_buttons_count, 1)
            isButtonsHorizontal = attributes.getBoolean(R.styleable.RateView_is_buttons_horizontal, true)
            buttonSpace = attributes.getFloat(R.styleable.RateView_button_space, 0f)
            numberSize = attributes.getFloat(R.styleable.RateView_number_size, 10f)
        } finally {
            attributes.recycle()
        }
    }

    private val paintDef = Paint().apply {
        color = defButtonColor
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = 5f
    }
    private val paintPushed = Paint().apply {
        color = pushedButtonColor
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = 5f
    }
    private val textPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        textSize = numberSize
        typeface = Typeface.DEFAULT
        color = Color.BLACK
        isAntiAlias = true
        textAlign = Paint.Align.CENTER
    }

    private val b = createButtons(buttonsCount, isButtonsHorizontal)
    private fun createButtons(count: Int, horizontal: Boolean): List<ButtonCoordinates> {
        val buttons = mutableListOf<ButtonCoordinates>()

        var cx = buttonSize + buttonSpace
        var cy = buttonSize + buttonSpace

        for (i in 0 until count) {
            buttons.add(ButtonCoordinates(cx, cy))

            when (horizontal) {
                true -> cx += (buttonSize * 2) + buttonSpace
                false -> cy += (buttonSize * 2) + buttonSpace
            }
        }
        return buttons
    }

    private fun pushButton(cx: Float, cy: Float) {
        val propertyX = PropertyValuesHolder.ofFloat("PROPERTY_X", abPosX, cx)
        val propertyY = PropertyValuesHolder.ofFloat("PROPERTY_Y", abPosY, cy)

        valueAnimator = ValueAnimator().apply {
            setValues(propertyX, propertyY)
            duration = 300
            addUpdateListener { anim ->
                abPosX = (anim.getAnimatedValue("PROPERTY_X") as Float)
                abPosY = (anim.getAnimatedValue("PROPERTY_Y") as Float)
                invalidate()
            }
        }
        valueAnimator?.start()!!
    }

    override fun onDraw(canvas: Canvas) {
        for (i in b.indices) {
            canvas.drawCircle(b[i].cx, b[i].cy, buttonSize, paintDef)
            canvas.drawCircle(abPosX, abPosY, abRadius, paintPushed)
            canvas.drawText((i + 1).toString(), b[i].cx, b[i].cy + (numberSize / 3), textPaint)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val corpWidth = b[b.size - 1].cx + buttonSize + buttonSpace
        val corpHeight = b[b.size - 1].cy + buttonSize + buttonSpace

        val width: Int
        val height: Int

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        width = when (widthMode) {
            MeasureSpec.EXACTLY -> widthSize
            // Can't be bigger than...
            MeasureSpec.AT_MOST -> min(widthSize, corpWidth.toInt())
            // Be whatever you want
            else -> corpWidth.toInt()
        }
        height = when (heightMode) {
            MeasureSpec.EXACTLY -> heightSize
            // Can't be bigger than...
            MeasureSpec.AT_MOST -> min(heightSize, corpHeight.toInt())
            // Be whatever you want
            else -> corpHeight.toInt()
        }
        setMeasuredDimension(width, height)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        val cX = event.number
        return super.onKeyDown(keyCode, event)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                pushButton(event.x, event.y)
            }
        }
        return super.onTouchEvent(event)
    }
}
