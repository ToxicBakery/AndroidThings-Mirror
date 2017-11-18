package com.toxicbakery.androidthings.mirror.job

import android.annotation.SuppressLint
import com.firebase.jobdispatcher.*
import com.github.salomonbrys.kodein.*
import com.toxicbakery.androidthings.mirror.kodein.JobServiceInjector

abstract class DispatchableJobService : JobService(), JobServiceInjector {

    final override val injector = KodeinInjector()
    final override val kodeinComponent = super.kodeinComponent
    final override val kodeinScope = super.kodeinScope

    override fun provideOverridingModule(): Kodein.Module = Kodein.Module {}

    final override fun initializeInjector() = super.initializeInjector()

    @SuppressLint("MissingSuperCall")
    final override fun destroyInjector() = super.destroyInjector()

    final override fun onStartJob(job: JobParameters): Boolean {
        initializeInjector()
        return startJob(job)
    }

    final override fun onStopJob(job: JobParameters): Boolean {
        destroyInjector()
        return stopJob(job)
    }

    abstract fun startJob(job: JobParameters): Boolean

    abstract fun stopJob(job: JobParameters): Boolean

    /**
     * Register the job to be run.
     */
    abstract fun dispatch(firebaseJobDispatcher: FirebaseJobDispatcher)

    /**
     * Cancel the job from future runs.
     */
    abstract fun cancel(firebaseJobDispatcher: FirebaseJobDispatcher)

}

val dispatcherJobServiceModule = Kodein.Module {
    bind<Driver>() with singleton { GooglePlayDriver(instance()) }
    bind<FirebaseJobDispatcher>() with singleton { FirebaseJobDispatcher(instance()) }
}