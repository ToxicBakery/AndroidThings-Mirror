package com.toxicbakery.androidthings.mirror.module.weather.forecast.kodein

import com.github.salomonbrys.kodein.Kodein
import com.toxicbakery.androidthings.mirror.module.weather.forecast.manager.forecastManagerModule
import com.toxicbakery.androidthings.mirror.module.weather.forecast.ui.presenter.forecastPresenterModule

val forecastKodein = Kodein {
    import(forecastPresenterModule)
    import(forecastManagerModule)
}