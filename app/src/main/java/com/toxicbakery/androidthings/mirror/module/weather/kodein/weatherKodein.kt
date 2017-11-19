package com.toxicbakery.androidthings.mirror.module.weather.kodein

import com.github.salomonbrys.kodein.Kodein
import com.toxicbakery.androidthings.mirror.module.weather.api.weatherApiModule
import com.toxicbakery.androidthings.mirror.module.weather.currentweather.kodein.currentWeatherKodein
import com.toxicbakery.androidthings.mirror.module.weather.forecast.kodein.forecastKodein
import com.toxicbakery.androidthings.mirror.module.weather.manager.zipCodeManagerModule

val weatherKodein = Kodein {
    import(weatherApiModule)
    import(zipCodeManagerModule)
}