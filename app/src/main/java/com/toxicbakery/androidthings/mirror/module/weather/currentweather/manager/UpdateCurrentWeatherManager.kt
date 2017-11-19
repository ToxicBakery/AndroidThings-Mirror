package com.toxicbakery.androidthings.mirror.module.weather.currentweather.manager

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.toxicbakery.androidthings.mirror.module.weather.currentweather.model.CurrentWeather
import com.toxicbakery.androidthings.mirror.module.weather.manager.ZipCodeManager
import com.toxicbakery.androidthings.mirror.module.weather.model.ZipCode
import io.reactivex.Observable
import timber.log.Timber

class UpdateCurrentWeatherManagerImpl(
        private val weatherManager: WeatherManager,
        private val zipCodeManager: ZipCodeManager
) : UpdateCurrentWeatherManager {

    override fun updateCurrentWeather(): Observable<CurrentWeather> =
            zipCodeManager.getZipCode()
                    .doOnNext { Timber.d("Updating Current Weather with Zip Code $it") }
                    .flatMap { zipCode: ZipCode -> weatherManager.updateCurrentWeather(zipCode) }

}

interface UpdateCurrentWeatherManager {
    fun updateCurrentWeather(): Observable<CurrentWeather>
}

val updateCurrentWeatherManagerModule = Kodein.Module {
    bind<UpdateCurrentWeatherManager>() with provider {
        UpdateCurrentWeatherManagerImpl(instance(), instance())
    }
}