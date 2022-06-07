package com.chanjungkim.myapp.util

import junit.framework.TestCase

class NumberUtilTest : TestCase() {
    fun testSum() {
        assertEquals(2, NumberUtil.sum(1,1))
    }

    fun testMultiply() {
        assertEquals(4, NumberUtil.multiply(2,2))
    }
}