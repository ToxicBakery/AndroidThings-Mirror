package com.toxicbakery.androidthings.mirror.module.weather.currentweather.manager

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.toxicbakery.androidthings.mirror.api.responseMapper
import com.toxicbakery.androidthings.mirror.module.weather.currentweather.api.WeatherApi
import com.toxicbakery.androidthings.mirror.module.weather.currentweather.model.CurrentWeather
import com.toxicbakery.androidthings.mirror.module.weather.manager.ZipCodeManager
import io.reactivex.Observable
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class WeatherManagerImpl(
        private val weatherApi: WeatherApi,
        private val zipCodeManager: ZipCodeManager
) : WeatherManager {

    override val currentWeather: Observable<CurrentWeather>
        get() = Observable.interval(0, 30, TimeUnit.MINUTES)
                .flatMap { zipCodeManager.zipCode.onErrorResumeNext(Observable.empty()) }
                .flatMap(weatherApi::currentWeather)
                .map { responseMapper(it) }

}

interface WeatherManager {

    val currentWeather: Observable<CurrentWeather>

}

val weatherManagerModule = Kodein.Module {
    bind<WeatherApi>() with provider { instance<Retrofit>().create(WeatherApi::class.java) }
    bind<WeatherManager>() with provider {
        WeatherManagerImpl(
                weatherApi = instance(),
                zipCodeManager = instance()
        )
    }
}