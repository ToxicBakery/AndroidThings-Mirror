package com.toxicbakery.androidthings.mirror.module.calendar.job

import com.firebase.jobdispatcher.FirebaseJobDispatcher
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.Trigger
import com.github.salomonbrys.kodein.instance
import com.toxicbakery.androidthings.mirror.job.DispatchableJobService
import com.toxicbakery.androidthings.mirror.module.calendar.manager.CalendarManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class CalendarJobService : DispatchableJobService() {

    private val calendarManager: CalendarManager by instance()

    private var subscription: Disposable = Disposables.disposed()

    override fun startJob(job: JobParameters): Boolean {
        Timber.d("Starting Calendar update.")
        subscription = calendarManager.updateCalendar()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate { jobFinished(job, true) }
                .subscribe(
                        { Timber.d("Updated Calendar") },
                        { Timber.e(it, "Failed to update Calendar") })
        return true
    }

    override fun stopJob(job: JobParameters): Boolean {
        Timber.d("Stopping Calendar update.")
        subscription.dispose()
        return false
    }

    override fun dispatch(firebaseJobDispatcher: FirebaseJobDispatcher) {
        firebaseJobDispatcher.newJobBuilder()
                .setService(CalendarJobService::class.java)
                .setTag(TAG)
                .setRecurring(true)
                // Execute every 30 to 60 minutes
                .setTrigger(Trigger.executionWindow(30 * 60, 60 * 60))
                .setReplaceCurrent(true)
                .build()
                .let { firebaseJobDispatcher.mustSchedule(it) }
                .let { Timber.d("Scheduled $TAG") }
    }

    override fun cancel(firebaseJobDispatcher: FirebaseJobDispatcher) {
        firebaseJobDispatcher.cancel(TAG)
    }

    companion object {
        private const val TAG = "CalendarJobService"
    }

}