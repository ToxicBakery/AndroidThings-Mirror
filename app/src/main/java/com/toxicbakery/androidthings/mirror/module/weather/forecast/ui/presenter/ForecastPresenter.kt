package com.toxicbakery.androidthings.mirror.module.weather.forecast.ui.presenter

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.toxicbakery.androidthings.mirror.job.JobManager
import com.toxicbakery.androidthings.mirror.module.weather.forecast.manager.ForecastManager
import com.toxicbakery.androidthings.mirror.module.weather.forecast.manager.UpdateForecastManager
import com.toxicbakery.androidthings.mirror.module.weather.forecast.ui.viewholder.ForecastViewHolder
import com.toxicbakery.androidthings.mirror.ui.presenter.Presenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class ForecastPresenterImpl(
        private val forecastManager: ForecastManager,
        private val updateForecastManager: UpdateForecastManager,
        private val jobManager: JobManager
) : ForecastPresenter {

    private val subscriptions: CompositeDisposable = CompositeDisposable()

    override fun bindViewHolder(viewHolder: ForecastViewHolder) {
        subscriptions.addAll(
                forecastManager.getForecast()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ viewHolder.bind(it) },
                                { Timber.e(it, "Failed to bind view holder") }),
                updateForecastManager.updateForecast()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { Timber.d("Updated Forecast") },
                                { Timber.e(it, "Failed to update Forecast") }))

        jobManager.registerJobs()
    }

    override fun unbindViewHolder() {
        subscriptions.clear()
        jobManager.unregisterJobs()
    }

}

interface ForecastPresenter : Presenter<ForecastViewHolder>

val forecastPresenterModule = Kodein.Module {
    bind<ForecastPresenter>() with provider {
        ForecastPresenterImpl(instance(), instance(), instance())
    }
}