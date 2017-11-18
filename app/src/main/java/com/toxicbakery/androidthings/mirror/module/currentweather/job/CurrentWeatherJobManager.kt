package com.toxicbakery.androidthings.mirror.module.currentweather.job

import com.firebase.jobdispatcher.FirebaseJobDispatcher
import com.github.salomonbrys.kodein.*
import com.toxicbakery.androidthings.mirror.job.DispatchableJobService

class CurrentWeatherJobManagerImpl(
        private val jobServiceSet: Set<DispatchableJobService>,
        private val firebaseJobDispatcher: FirebaseJobDispatcher
) : CurrentWeatherJobManager {

    override fun resigsterJobs() {
        jobServiceSet.forEach { it.dispatch(firebaseJobDispatcher) }
    }

    override fun unregisterJobs() {
        jobServiceSet.forEach { it.cancel(firebaseJobDispatcher) }
    }


}

interface CurrentWeatherJobManager {
    fun resigsterJobs()
    fun unregisterJobs()
}

val currentWeatherJobManagerModule = Kodein.Module {
    bind() from setBinding<DispatchableJobService>()
    bind<DispatchableJobService>().inSet() with provider { CurrentWeatherJobService() }
    bind<CurrentWeatherJobManager>() with provider { CurrentWeatherJobManagerImpl(instance(), instance()) }
}