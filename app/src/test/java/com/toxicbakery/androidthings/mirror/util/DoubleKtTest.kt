package com.toxicbakery.androidthings.mirror.util

import org.junit.Assert.assertEquals
import org.junit.Test

class DoubleKtTest {

    @Test
    fun round() {
        assertEquals(1, 1.0.round())
        assertEquals(1, 1.49.round())
        assertEquals(2, 1.5.round())
    }

}