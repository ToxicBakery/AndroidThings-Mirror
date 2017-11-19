package com.toxicbakery.androidthings.mirror.module.weather.currentweather.kodein

import com.github.salomonbrys.kodein.Kodein
import com.toxicbakery.androidthings.mirror.module.weather.currentweather.job.currentWeatherJobManagerModule
import com.toxicbakery.androidthings.mirror.module.weather.currentweather.manager.updateCurrentWeatherManagerModule
import com.toxicbakery.androidthings.mirror.module.weather.currentweather.manager.weatherManagerModule
import com.toxicbakery.androidthings.mirror.module.weather.currentweather.ui.presenter.currentWeatherPresenterModule

val moduleCurrentWeatherKodein = Kodein {
    import(currentWeatherJobManagerModule)
    import(currentWeatherPresenterModule)
    import(updateCurrentWeatherManagerModule)
    import(weatherManagerModule)
}