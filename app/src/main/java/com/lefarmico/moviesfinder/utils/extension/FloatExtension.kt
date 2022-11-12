package com.lefarmico.moviesfinder.utils.extension

import java.math.BigDecimal
import java.math.RoundingMode

fun Float.roundTo(places: Int): Float = BigDecimal(this.toDouble())
    .run { setScale(places, RoundingMode.HALF_UP) }
    .toFloat()
