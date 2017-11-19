package com.toxicbakery.androidthings.mirror.module.weather.forecast.model

import com.toxicbakery.androidthings.mirror.module.weather.model.Clouds
import com.toxicbakery.androidthings.mirror.module.weather.model.Rain
import com.toxicbakery.androidthings.mirror.module.weather.model.Snow
import com.toxicbakery.androidthings.mirror.module.weather.model.Wind

data class ForecastRow(
        val dt: Long,
        val main: Main,
        val weather: Weather,
        val clouds: Clouds,
        val wind: Wind,
        val rain: Rain = Rain(0),
        val snow: Snow = Snow(0)
)