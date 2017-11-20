package com.toxicbakery.androidthings.mirror.module.calendar.job

import com.firebase.jobdispatcher.FirebaseJobDispatcher
import com.github.salomonbrys.kodein.*
import com.toxicbakery.androidthings.mirror.job.DispatchableJobService
import com.toxicbakery.androidthings.mirror.job.JobManager

class CalendarJobManagerImpl(
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

val calendarJobManagerModule = Kodein.Module {
    bind() from setBinding<DispatchableJobService>()
    bind<DispatchableJobService>().inSet() with provider { CalendarJobService() }
    bind<JobManager>() with provider { CalendarJobManagerImpl(instance(), instance()) }
}