package com.lefarmico.moviesfinder.customViews

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateInterpolator
import com.lefarmico.moviesfinder.R
import kotlin.math.min

class RateView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : View(context, attributeSet) {
    data class ButtonCoordinates(val cx: Float, val cy: Float)
    data class PushedButton(var isPushed: Boolean, var pressedCX: Float, var pressedCY: Float, var pressedB: Int)

    private val TAG = this.javaClass.canonicalName
    private var valueAnimator: ValueAnimator? = null

    private var buttonSize = 0f
    private var numberSize = 0f
    private var buttonSpace = 20f
    private var defButtonColor = Color.parseColor("#E0FFFF")
    private var pushedButtonColor = Color.parseColor("#F0E82D")
    private var buttonsCount: Int = 0
    private var isButtonsHorizontal = true

    private var pressedCX = 0f
    private var pressedCY = 0f
    var pressedB: Int = 0

    private var abPosX = 0f
    private var abPosY = 0f
    private var abRadius = 0f

    init {

        val attributes = context.theme.obtainStyledAttributes(attributeSet, R.styleable.RateView, 0, 0)

        try {
            buttonSize = attributes.getDimension(R.styleable.RateView_button_size, 0f)
            defButtonColor = attributes.getColor(R.styleable.RateView_default_color, Color.WHITE)
            pushedButtonColor = attributes.getColor(R.styleable.RateView_pushed_color, Color.YELLOW)
            buttonsCount = attributes.getInt(R.styleable.RateView_buttons_count, 1)
            isButtonsHorizontal = attributes.getBoolean(R.styleable.RateView_is_buttons_horizontal, true)
            buttonSpace = attributes.getDimension(R.styleable.RateView_button_space, 0f)
            numberSize = attributes.getDimension(R.styleable.RateView_number_size, 10f)
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

    private val b = createButtons(count = buttonsCount, horizontal = isButtonsHorizontal)
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

    fun setPushedButton(pushedNumber: Int) {
        if (pushedNumber != 0) {
            pressedB = pushedNumber
            abPosX = b[pushedNumber - 1].cx
            abPosY = b[pushedNumber - 1].cy
            abRadius = buttonSize
        }
        invalidate()
    }

    private fun pushButton(cx: Float, cy: Float) {
        for (i in b.indices) {
            if (cx >= b[i].cx - buttonSize && cx <= b[i].cx + buttonSize && cy >= b[i].cy - buttonSize && cy <= b[i].cy + buttonSize) {
                pressedCX = b[i].cx
                pressedCY = b[i].cy

                if (pressedB == 0) {
                    pressedB = i + 1
                    abPosX = pressedCX
                    abPosY = pressedCY
                    invalidate()
                }
            }
        }

        val propertyX = PropertyValuesHolder.ofFloat("PROPERTY_X", abPosX, pressedCX)
        val propertyY = PropertyValuesHolder.ofFloat("PROPERTY_Y", abPosY, pressedCY)
        val propertyR = PropertyValuesHolder.ofFloat("PROPERTY_R", abRadius, buttonSize)

        valueAnimator = ValueAnimator().apply {
            setValues(propertyX, propertyY, propertyR)
            duration = 200
            interpolator = AccelerateInterpolator()
            addUpdateListener { anim ->
                abPosX = (anim.getAnimatedValue("PROPERTY_X") as Float)
                abPosY = (anim.getAnimatedValue("PROPERTY_Y") as Float)
                if (abRadius != buttonSize)
                    abRadius = (anim.getAnimatedValue("PROPERTY_R") as Float)
                invalidate()
            }
        }
        valueAnimator?.start()!!
    }

    override fun onDraw(canvas: Canvas) {
        for (i in b.indices) {
            canvas.drawCircle(b[i].cx, b[i].cy, buttonSize, paintDef)
        }

        canvas.drawCircle(abPosX, abPosY, abRadius, paintPushed)

        for (i in b.indices) {
            canvas.drawText((i + 1).toString(), b[i].cx, b[i].cy + (numberSize / 3), textPaint)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setPushedButton(pressedB)

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
        val cX = event.action
        return super.onKeyDown(keyCode, event)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.d("Action", "Action down")
                return true
            }
            MotionEvent.ACTION_UP -> {
                Log.d("Action", "Action up")
                pushButton(event.x, event.y)
            }
            MotionEvent.ACTION_MOVE -> {
                Log.d("Action", "Action move")
            }
        }
        invalidate()
        return super.onTouchEvent(event)
    }
}
