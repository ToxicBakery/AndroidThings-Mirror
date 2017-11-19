package com.toxicbakery.androidthings.mirror.module.weather.forecast.model

data class Forecast(
        val city: City,
        val list: List<ForecastRow>
)