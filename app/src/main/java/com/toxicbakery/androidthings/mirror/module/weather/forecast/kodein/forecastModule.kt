package com.toxicbakery.androidthings.mirror.module.weather.forecast.kodein

import com.github.salomonbrys.kodein.Kodein
import com.toxicbakery.androidthings.mirror.module.weather.forecast.manager.forecastManagerModule
import com.toxicbakery.androidthings.mirror.module.weather.forecast.ui.presenter.forecastPresenterModule

val forecastModule = Kodein.Module {
    import(forecastPresenterModule)
    import(forecastManagerModule)
}