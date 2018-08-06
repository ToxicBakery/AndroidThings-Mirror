package com.toxicbakery.androidthings.mirror.module.weather.currentweather.kodein

import com.github.salomonbrys.kodein.Kodein
import com.toxicbakery.androidthings.mirror.module.weather.currentweather.manager.weatherManagerModule
import com.toxicbakery.androidthings.mirror.module.weather.currentweather.ui.presenter.currentWeatherPresenterModule

val currentWeatherModule = Kodein.Module {
    import(currentWeatherPresenterModule)
    import(weatherManagerModule)
}