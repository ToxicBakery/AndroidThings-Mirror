package com.toxicbakery.androidthings.mirror.module.weather.model

data class Weather(
        val id: Int,
        val main: String,
        val description: String,
        val icon: String
)