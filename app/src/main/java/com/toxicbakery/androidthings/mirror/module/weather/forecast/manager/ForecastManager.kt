package com.toxicbakery.androidthings.mirror.module.weather.forecast.manager

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.toxicbakery.androidthings.mirror.api.responseMapper
import com.toxicbakery.androidthings.mirror.module.weather.forecast.api.ForecastApi
import com.toxicbakery.androidthings.mirror.module.weather.forecast.model.Forecast
import com.toxicbakery.androidthings.mirror.module.weather.forecast.store.ForecastStore
import com.toxicbakery.androidthings.mirror.module.weather.forecast.store.forecastStoreModule
import com.toxicbakery.androidthings.mirror.module.weather.model.ZipCode
import io.reactivex.Observable
import retrofit2.Retrofit

class ForecastManagerImpl(
        private val forecastApi: ForecastApi,
        private val forecastStore: ForecastStore
) : ForecastManager {

    override fun getForecast(): Observable<Forecast> =
            forecastStore.forecastObservable

    override fun updateCurrentWeather(zipCode: ZipCode): Observable<Forecast> =
            forecastApi.forecast(zipCode)
                    .map { responseMapper(it) }
                    .doOnNext { forecastStore.saveForecast(it) }

}

interface ForecastManager {

    fun getForecast(): Observable<Forecast>

    fun updateCurrentWeather(zipCode: ZipCode): Observable<Forecast>

}

val forecastManagerModule = Kodein.Module {
    import(forecastStoreModule)
    bind<ForecastApi>() with provider { instance<Retrofit>().create(ForecastApi::class.java) }
    bind<ForecastManager>() with provider { ForecastManagerImpl(instance(), instance()) }
}