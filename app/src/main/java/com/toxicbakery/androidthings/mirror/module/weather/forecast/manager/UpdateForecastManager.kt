package com.toxicbakery.androidthings.mirror.module.weather.forecast.manager

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.toxicbakery.androidthings.mirror.module.weather.forecast.model.Forecast
import com.toxicbakery.androidthings.mirror.module.weather.manager.ZipCodeManager
import com.toxicbakery.androidthings.mirror.module.weather.model.ZipCode
import io.reactivex.Observable
import timber.log.Timber

class UpdateForecastManagerImpl(
        private val forecastManager: ForecastManager,
        private val zipCodeManager: ZipCodeManager
) : UpdateForecastManager {

    override fun updateForecast(): Observable<Forecast> =
            zipCodeManager.getZipCode()
                    .doOnNext { Timber.d("Updating Forecast with Zip Code $it") }
                    .flatMap { zipCode: ZipCode -> forecastManager.updateCurrentWeather(zipCode) }
}

interface UpdateForecastManager {
    fun updateForecast(): Observable<Forecast>
}

val updateForecastManagerModule = Kodein.Module {
    bind<UpdateForecastManager>() with provider {
        UpdateForecastManagerImpl(instance(), instance())
    }
}