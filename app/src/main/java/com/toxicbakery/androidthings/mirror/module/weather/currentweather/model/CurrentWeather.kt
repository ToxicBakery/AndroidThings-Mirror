package com.toxicbakery.androidthings.mirror.module.weather.currentweather.model

import com.toxicbakery.androidthings.mirror.module.weather.model.*

data class CurrentWeather(
        val coord: Coord,
        val weather: List<Weather>,
        val base: String,
        val main: Main,
        val visibility: Int,
        val wind: Wind,
        val clouds: Clouds = Clouds(0),
        val rain: Rain = Rain(),
        val snow: Snow = Snow(),
        val dt: Long,
        val sys: Sys,
        val name: String
)