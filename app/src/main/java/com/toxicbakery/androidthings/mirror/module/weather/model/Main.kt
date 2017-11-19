package com.toxicbakery.androidthings.mirror.module.weather.model

data class Main(
        val temp: Double,
        val pressure: Double,
        val humidity: Int,
        val temp_min: Double,
        val temp_max: Double,
        val sea_level: Double,
        val grnd_level: Double
)