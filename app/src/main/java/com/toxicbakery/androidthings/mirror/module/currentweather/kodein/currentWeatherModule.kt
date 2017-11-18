package com.toxicbakery.androidthings.mirror.module.currentweather.kodein

import com.github.salomonbrys.kodein.Kodein
import com.toxicbakery.androidthings.mirror.module.currentweather.api.weatherApiModule
import com.toxicbakery.androidthings.mirror.module.currentweather.job.currentWeatherJobManagerModule
import com.toxicbakery.androidthings.mirror.module.currentweather.manager.updateCurrentWeatherManagerModule
import com.toxicbakery.androidthings.mirror.module.currentweather.manager.weatherManagerModule
import com.toxicbakery.androidthings.mirror.module.currentweather.manager.zipCodeManagerModule
import com.toxicbakery.androidthings.mirror.module.currentweather.ui.presenter.currentWeatherPresenterModule

val moduleCurrentWeatherKodein = Kodein {
    import(currentWeatherJobManagerModule)
    import(currentWeatherPresenterModule)
    import(updateCurrentWeatherManagerModule)
    import(weatherApiModule)
    import(weatherManagerModule)
    import(zipCodeManagerModule)
}