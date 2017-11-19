package com.toxicbakery.androidthings.mirror.module.weather.kodein

import com.github.salomonbrys.kodein.Kodein
import com.toxicbakery.androidthings.mirror.module.weather.api.weatherApiModule
import com.toxicbakery.androidthings.mirror.module.weather.currentweather.kodein.moduleCurrentWeatherKodein
import com.toxicbakery.androidthings.mirror.module.weather.forecast.kodein.moduleForecastKodein
import com.toxicbakery.androidthings.mirror.module.weather.manager.zipCodeManagerModule

val moduleWeatherKodein = Kodein {
    extend(moduleForecastKodein)
    extend(moduleCurrentWeatherKodein)

    import(weatherApiModule)
    import(zipCodeManagerModule)
}