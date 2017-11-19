package com.toxicbakery.androidthings.mirror.module.weather.model

import com.google.gson.annotations.SerializedName

data class Snow(
        @SerializedName("3h")
        val threeHours: Int
)