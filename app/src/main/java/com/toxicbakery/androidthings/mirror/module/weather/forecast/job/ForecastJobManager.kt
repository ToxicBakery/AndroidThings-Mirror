package com.toxicbakery.androidthings.mirror.module.weather.forecast.job

import com.firebase.jobdispatcher.FirebaseJobDispatcher
import com.github.salomonbrys.kodein.*
import com.toxicbakery.androidthings.mirror.job.DispatchableJobService
import com.toxicbakery.androidthings.mirror.job.JobManager

class ForecastJobManagerImpl(
        private val jobServiceSet: Set<DispatchableJobService>,
        private val firebaseJobDispatcher: FirebaseJobDispatcher
) : JobManager {

    override fun registerJobs() {
        jobServiceSet.forEach { it.dispatch(firebaseJobDispatcher) }
    }

    override fun unregisterJobs() {
        jobServiceSet.forEach { it.cancel(firebaseJobDispatcher) }
    }

}

val forecastJobManagerModule = Kodein.Module {
    bind() from setBinding<DispatchableJobService>()
    bind<DispatchableJobService>().inSet() with provider { ForecastJobService() }
    bind<JobManager>() with provider { ForecastJobManagerImpl(instance(), instance()) }
}