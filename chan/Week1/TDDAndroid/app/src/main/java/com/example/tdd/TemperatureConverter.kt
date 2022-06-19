package com.example.tdd

import kotlin.math.roundToInt

object TemperatureConverter {
    const val ABSOLUTE_ZERO_C = -273

    fun celsiusToFahrenheit(c: Int): Int {
        if (c < ABSOLUTE_ZERO_C) {
            throw RuntimeException("Invalid temperature: $c below absolute zero")
        }
        return (c * 1.8 + 32).roundToInt()
    }
}