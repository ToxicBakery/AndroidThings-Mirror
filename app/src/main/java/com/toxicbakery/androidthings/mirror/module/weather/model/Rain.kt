package com.toxicbakery.androidthings.mirror.module.weather.model

import com.google.gson.annotations.SerializedName

data class Rain(
        @SerializedName("3h")
        val threeHours: Double = 0.0
)