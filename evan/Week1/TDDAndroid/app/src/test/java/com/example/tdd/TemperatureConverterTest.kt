package com.example.tdd

import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test

class TemperatureConverterTest {
    private val conversionTable = hashMapOf<Int, Int>()

    init {
        conversionTable[0] = 32
        conversionTable[100] = 212
        conversionTable[-1] = 30
        conversionTable[-100] = -148
        conversionTable[32] = 90
        conversionTable[-40] = -40
        conversionTable[-273] = -459
    }

    @Test
    fun testCelsiusToFahrenheit() {
        conversionTable.forEach {
            val c = it.key
            val f = it.value
            val msg = "$c C -> $f F"
            assertEquals(msg, it.value, TemperatureConverter.celsiusToFahrenheit(c))
        }
    }

    @Test
    fun testInvalidCelsiusTemperature() {
        try {
            val f = TemperatureConverter.celsiusToFahrenheit(-274)
        } catch (ex: RuntimeException) {
            if (ex.message!!.contains("below absolute zero")) {
                return
            } else {
                fail("Undetected temperature below absolute zero: " + ex.message)
            }
        }
        fail("Undetected temperature below absolute zero: no exception generated")
    }
}