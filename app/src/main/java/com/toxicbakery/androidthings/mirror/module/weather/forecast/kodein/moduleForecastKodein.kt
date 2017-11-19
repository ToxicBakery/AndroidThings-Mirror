package com.toxicbakery.androidthings.mirror.module.weather.forecast.kodein

import com.github.salomonbrys.kodein.Kodein
import com.toxicbakery.androidthings.mirror.module.weather.forecast.job.forecastJobManagerModule
import com.toxicbakery.androidthings.mirror.module.weather.forecast.manager.forecastManagerModule
import com.toxicbakery.androidthings.mirror.module.weather.forecast.manager.updateForecastManagerModule
import com.toxicbakery.androidthings.mirror.module.weather.forecast.ui.presenter.forecastPresenterModule

val moduleForecastKodein = Kodein {
    import(forecastJobManagerModule)
    import(forecastPresenterModule)
    import(updateForecastManagerModule)
    import(forecastManagerModule)
}