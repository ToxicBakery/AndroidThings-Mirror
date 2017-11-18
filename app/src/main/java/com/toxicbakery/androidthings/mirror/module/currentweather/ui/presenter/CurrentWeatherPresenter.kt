package com.toxicbakery.androidthings.mirror.module.currentweather.ui.presenter

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.toxicbakery.androidthings.mirror.module.currentweather.job.CurrentWeatherJobManager
import com.toxicbakery.androidthings.mirror.module.currentweather.manager.UpdateCurrentWeatherManager
import com.toxicbakery.androidthings.mirror.module.currentweather.manager.WeatherManager
import com.toxicbakery.androidthings.mirror.module.currentweather.ui.viewholder.CurrentWeatherViewHolder
import com.toxicbakery.androidthings.mirror.ui.presenter.Presenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class CurrentWeatherPresenterImpl(
        private val weatherManager: WeatherManager,
        private val updateCurrentWeatherManager: UpdateCurrentWeatherManager,
        private val currentWeatherJobManager: CurrentWeatherJobManager
) : CurrentWeatherPresenter {

    private val subscriptions: CompositeDisposable = CompositeDisposable()

    override fun bindViewHolder(viewHolder: CurrentWeatherViewHolder) {
        subscriptions.addAll(
                weatherManager.getCurrentWeather()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ viewHolder.bind(it) },
                                { Timber.e(it, "Failed to bind view holder") }),
                updateCurrentWeatherManager.updateCurrentWeather()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { Timber.d("Updated Current Weather") },
                                { Timber.e(it, "Failed to update Current Weather") }))

        currentWeatherJobManager.resigsterJobs()
    }

    override fun unbindViewHolder() {
        subscriptions.clear()
        currentWeatherJobManager.unregisterJobs()
    }

}

interface CurrentWeatherPresenter : Presenter<CurrentWeatherViewHolder>

val currentWeatherPresenterModule = Kodein.Module {
    bind<CurrentWeatherPresenter>() with provider {
        CurrentWeatherPresenterImpl(instance(), instance(), instance())
    }
}