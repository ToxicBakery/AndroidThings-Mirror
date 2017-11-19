package com.toxicbakery.androidthings.mirror.module.weather.forecast.job

import com.firebase.jobdispatcher.FirebaseJobDispatcher
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.Trigger
import com.github.salomonbrys.kodein.instance
import com.toxicbakery.androidthings.mirror.job.DispatchableJobService
import com.toxicbakery.androidthings.mirror.module.weather.forecast.manager.UpdateForecastManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class ForecastJobService : DispatchableJobService() {

    private val updateForecastManager: UpdateForecastManager by instance()

    private var subscription: Disposable = Disposables.disposed()

    override fun startJob(job: JobParameters): Boolean {
        Timber.d("Starting Forecast update.")
        subscription = updateForecastManager.updateForecast()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate { jobFinished(job, true) }
                .subscribe(
                        { Timber.d("Updated Forecast with report.") },
                        { Timber.e(it, "Failed to update Forecast") }
                )
        return true
    }

    override fun stopJob(job: JobParameters): Boolean {
        Timber.d("Stopping Forecast update.")
        subscription.dispose()
        return false
    }

    override fun dispatch(firebaseJobDispatcher: FirebaseJobDispatcher) {
        firebaseJobDispatcher.newJobBuilder()
                .setService(ForecastJobService::class.java)
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
        private const val TAG = "ForecastJobService"
    }

}