package com.toxicbakery.androidthings.mirror.module.currentweather.model

data class CurrentWeather(
        val coord: Coord,
        val weather: List<Weather>,
        val base: String,
        val main: Main,
        val visibility: Int,
        val wind: Wind,
        val clouds: Clouds = Clouds(0),
        val rain: Rain = Rain(0),
        val snow: Snow = Snow(0),
        val dt: Long,
        val sys: Sys,
        val name: String
)