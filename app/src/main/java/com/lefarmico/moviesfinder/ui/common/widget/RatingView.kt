package com.lefarmico.moviesfinder.ui.common.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.constraintlayout.widget.ConstraintLayout
import com.lefarmico.moviesfinder.databinding.RatingViewBinding

class RatingView(context: Context, @Nullable attributeSet: AttributeSet) : ConstraintLayout(context, attributeSet) {

    private var binding: RatingViewBinding =
        RatingViewBinding.inflate(LayoutInflater.from(context), this, true)

    private val ratingValue: TextView = binding.rating

    fun setRatingValue(value: Float) {
        if (value > 0.0 && value <= 10.0) {
            ratingValue.text = value.toString()
        } else {
            ratingValue.text = 0.0.toString()
        }
    }
}
