package com.toxicbakery.androidthings.mirror.module.weather.forecast.model

import com.toxicbakery.androidthings.mirror.module.weather.model.Coord

data class City(
        val id: String,
        val name: String,
        val coord: Coord,
        val country: String
)