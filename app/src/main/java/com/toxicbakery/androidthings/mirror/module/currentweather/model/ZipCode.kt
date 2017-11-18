package com.toxicbakery.androidthings.mirror.module.currentweather.model

import android.support.annotation.IntRange
import android.support.annotation.Size

data class ZipCode(
        @IntRange(from = 10000, to = 99999)
        val zip: Int,
        @Size(2)
        val countryCode: String
) {
    override fun toString(): String = "$zip,$countryCode"
}