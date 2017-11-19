package com.toxicbakery.androidthings.mirror.module.weather.currentweather.store

import com.github.salomonbrys.kodein.*
import com.toxicbakery.androidthings.mirror.module.weather.currentweather.model.CurrentWeather
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

class WeatherStoreImpl(
        private val subject: Subject<CurrentWeather>
) : WeatherStore {

    override val currentWeatherObservable: Observable<CurrentWeather>
        get() = subject

    override fun saveCurrentWeather(currentWeather: CurrentWeather) = subject.onNext(currentWeather)

}

interface WeatherStore {

    val currentWeatherObservable: Observable<CurrentWeather>

    fun saveCurrentWeather(currentWeather: CurrentWeather)

}

val weatherStoreModule = Kodein.Module {
    bind<Subject<CurrentWeather>>("SUBJECT_CURRENT_WEATHER") with singleton {
        BehaviorSubject.create<CurrentWeather>()
    }
    bind<WeatherStore>() with provider { WeatherStoreImpl(instance("SUBJECT_CURRENT_WEATHER")) }
}