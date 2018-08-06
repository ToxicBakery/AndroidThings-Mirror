package com.toxicbakery.androidthings.mirror.module.weather.forecast.manager

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.toxicbakery.androidthings.mirror.api.responseMapper
import com.toxicbakery.androidthings.mirror.module.weather.forecast.api.ForecastApi
import com.toxicbakery.androidthings.mirror.module.weather.forecast.model.Forecast
import com.toxicbakery.androidthings.mirror.module.weather.manager.ZipCodeManager
import io.reactivex.Observable
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class ForecastManagerImpl(
        private val forecastApi: ForecastApi,
        private val zipCodeManager: ZipCodeManager
) : ForecastManager {

    override fun getForecast(): Observable<Forecast> =
            Observable.interval(0, 30, TimeUnit.MINUTES)
                    .flatMap { zipCodeManager.zipCode.onErrorResumeNext(Observable.empty()) }
                    .flatMap(forecastApi::forecast)
                    .map { responseMapper(it) }

}

interface ForecastManager {

    fun getForecast(): Observable<Forecast>

}

val forecastManagerModule = Kodein.Module {
    bind<ForecastApi>() with provider { instance<Retrofit>().create(ForecastApi::class.java) }
    bind<ForecastManager>() with provider {
        ForecastManagerImpl(
                forecastApi = instance(),
                zipCodeManager = instance()
        )
    }
}