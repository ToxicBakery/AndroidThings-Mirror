package com.toxicbakery.androidthings.mirror.module.currentweather.manager

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.toxicbakery.androidthings.mirror.api.ResponseException
import com.toxicbakery.androidthings.mirror.module.currentweather.api.WeatherApi
import com.toxicbakery.androidthings.mirror.module.currentweather.model.CurrentWeather
import com.toxicbakery.androidthings.mirror.module.currentweather.model.ZipCode
import com.toxicbakery.androidthings.mirror.module.currentweather.store.WeatherStore
import com.toxicbakery.androidthings.mirror.module.currentweather.store.weatherStoreModule
import io.reactivex.Observable
import retrofit2.Retrofit

class WeatherManagerImpl(
        private val weatherApi: WeatherApi,
        private val weatherStore: WeatherStore
) : WeatherManager {

    override fun getCurrentWeather(): Observable<CurrentWeather> =
            weatherStore.currentWeatherObservable

    override fun updateCurrentWeather(zipCode: ZipCode): Observable<CurrentWeather> =
            weatherApi.currentWeather(zipCode)
                    .map {
                        if (it.isSuccessful) it.body()!!
                        else throw ResponseException(it.code())
                    }
                    .doOnNext { weatherStore.saveCurrentWeather(it) }

}

interface WeatherManager {

    fun getCurrentWeather(): Observable<CurrentWeather>

    fun updateCurrentWeather(zipCode: ZipCode): Observable<CurrentWeather>

}

val weatherManagerModule = Kodein.Module {
    import(weatherStoreModule)
    bind<WeatherApi>() with provider { instance<Retrofit>().create(WeatherApi::class.java) }
    bind<WeatherManager>() with provider { WeatherManagerImpl(instance(), instance()) }
}