package com.toxicbakery.androidthings.mirror.module.weather.forecast.model

import com.toxicbakery.androidthings.mirror.module.weather.model.*

data class ForecastRow(
        val dt: Long,
        val main: Main,
        val weather: List<Weather>,
        val clouds: Clouds,
        val wind: Wind,
        val rain: Rain = Rain(),
        val snow: Snow = Snow()
)