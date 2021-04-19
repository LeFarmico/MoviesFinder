package com.lefarmico.moviesfinder.customViews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.constraintlayout.widget.ConstraintLayout
import com.lefarmico.moviesfinder.databinding.RatingViewBinding

class RatingView(context: Context, @Nullable attributeSet: AttributeSet) : ConstraintLayout(context, attributeSet) {

    private var binding: RatingViewBinding

    private val ratingValue: TextView

    init {
        binding = RatingViewBinding.inflate(LayoutInflater.from(context), this, true)

        ratingValue = binding.rating
    }

    fun setRatingValue(value: Double) {
        if (value > 0.0 && value <= 10.0) {
            ratingValue.text = value.toString()
        } else {
            ratingValue.text = 0.0.toString()
        }
    }
}