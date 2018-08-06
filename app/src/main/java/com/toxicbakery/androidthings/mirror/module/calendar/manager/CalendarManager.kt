package com.toxicbakery.androidthings.mirror.module.calendar.manager

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.toxicbakery.androidthings.mirror.module.calendar.api.CalendarApi
import io.reactivex.Observable
import net.fortuna.ical4j.model.Calendar
import timber.log.Timber
import java.util.concurrent.TimeUnit

class CalendarManagerImpl(
        private val calendarApi: CalendarApi
) : CalendarManager {

    private val calendarSource: Observable<Calendar>
        get() = calendarApi.calendar
                .doOnError { Timber.e(it, "Error fetching calendar.") }
                .onErrorResumeNext(Observable.empty())

    override val calendar: Observable<Calendar>
        get() = Observable.interval(0, 30, TimeUnit.MINUTES)
                .flatMap { calendarSource }

}

interface CalendarManager {

    val calendar: Observable<Calendar>

}

val calendarManagerModule = Kodein.Module {
    bind<CalendarManager>() with provider {
        CalendarManagerImpl(
                calendarApi = instance()
        )
    }
}