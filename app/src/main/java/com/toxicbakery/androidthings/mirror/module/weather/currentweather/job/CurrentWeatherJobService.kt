package com.toxicbakery.androidthings.mirror.module.weather.currentweather.job

import com.firebase.jobdispatcher.FirebaseJobDispatcher
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.Trigger
import com.github.salomonbrys.kodein.instance
import com.toxicbakery.androidthings.mirror.job.DispatchableJobService
import com.toxicbakery.androidthings.mirror.module.weather.currentweather.manager.UpdateCurrentWeatherManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class CurrentWeatherJobService : DispatchableJobService() {

    private val updateCurrentWeatherManager: UpdateCurrentWeatherManager by instance()

    private var subscription: Disposable = Disposables.disposed()

    override fun startJob(job: JobParameters): Boolean {
        Timber.d("Starting Current Weather update.")
        subscription = updateCurrentWeatherManager.updateCurrentWeather()
                .map { Date(it.dt * 1000L).let { SimpleDateFormat("h:mm a", Locale.ENGLISH).format(it) } }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate { jobFinished(job, true) }
                .subscribe(
                        { Timber.d("Updated Current Weather with report from $it") },
                        { Timber.e(it, "Failed to update Current Weather") }
                )
        return true
    }

    override fun stopJob(job: JobParameters): Boolean {
        Timber.d("Stopping Current Weather update.")
        subscription.dispose()
        return false
    }

    override fun dispatch(firebaseJobDispatcher: FirebaseJobDispatcher) {
        firebaseJobDispatcher.newJobBuilder()
                .setService(CurrentWeatherJobService::class.java)
                .setTag(TAG)
                .setRecurring(true)
                // Execute every 15 to 20 minutes
                .setTrigger(Trigger.executionWindow(15 * 60, 20 * 60))
                .setReplaceCurrent(true)
                .build()
                .let { firebaseJobDispatcher.mustSchedule(it) }
                .let { Timber.d("Scheduled $TAG") }
    }

    override fun cancel(firebaseJobDispatcher: FirebaseJobDispatcher) {
        firebaseJobDispatcher.cancel(TAG)
    }

    companion object {
        private const val TAG = "CurrentWeatherJobService"
    }

}