package com.toxicbakery.androidthings.mirror.module.weather.forecast.store

import com.github.salomonbrys.kodein.*
import com.toxicbakery.androidthings.mirror.module.weather.forecast.model.Forecast
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

class ForecastStoreImpl(
        private val subject: Subject<Forecast>
) : ForecastStore {

    override val forecastObservable: Observable<Forecast>
        get() = subject

    override fun saveForecast(forecast: Forecast) = subject.onNext(forecast)

}

interface ForecastStore {

    val forecastObservable: Observable<Forecast>

    fun saveForecast(forecast: Forecast)

}

val forecastStoreModule = Kodein.Module {
    bind<Subject<Forecast>>("SUBJECT_FORECAST") with singleton {
        BehaviorSubject.create<Forecast>()
    }
    bind<ForecastStore>() with provider { ForecastStoreImpl(instance("SUBJECT_FORECAST")) }
}