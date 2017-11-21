package com.toxicbakery.androidthings.mirror.util

import android.view.View
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ViewKtTest {

    @Test
    fun isNotInEditMode() {
        val view = mock<View>()
        whenever(view.isInEditMode).thenReturn(true)
        assertFalse(view.isNotInEditMode)
        whenever(view.isInEditMode).thenReturn(false)
        assertTrue(view.isNotInEditMode)
    }

}