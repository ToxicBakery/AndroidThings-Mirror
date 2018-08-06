package com.toxicbakery.androidthings.mirror.module.weather.currentweather.ui.presenter

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.toxicbakery.androidthings.mirror.module.weather.currentweather.manager.WeatherManager
import com.toxicbakery.androidthings.mirror.module.weather.currentweather.ui.viewholder.CurrentWeatherViewHolder
import com.toxicbakery.androidthings.mirror.ui.presenter.Presenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class CurrentWeatherPresenterImpl(
        private val weatherManager: WeatherManager
) : CurrentWeatherPresenter {

    private val subscriptions: CompositeDisposable = CompositeDisposable()

    override fun bindViewHolder(viewHolder: CurrentWeatherViewHolder) {
        subscriptions.addAll(
                weatherManager.currentWeather
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { viewHolder.bind(it) },
                                { Timber.e(it, "Failed to bind view holder") }))

    }

    override fun unbindViewHolder() {
        subscriptions.clear()
    }

}

interface CurrentWeatherPresenter : Presenter<CurrentWeatherViewHolder>

val currentWeatherPresenterModule = Kodein.Module {
    bind<CurrentWeatherPresenter>() with provider {
        CurrentWeatherPresenterImpl(
                weatherManager = instance()
        )
    }
}