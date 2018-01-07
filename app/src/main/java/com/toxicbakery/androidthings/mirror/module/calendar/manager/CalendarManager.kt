package com.toxicbakery.androidthings.mirror.module.calendar.manager

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.toxicbakery.androidthings.mirror.module.calendar.api.CalendarApi
import com.toxicbakery.library.ical.Ical
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class CalendarManagerImpl(
        private val calendarApi: CalendarApi
) : CalendarManager {

    override fun getCalendar(): Observable<Ical> =
            Observable.interval(0, 30, TimeUnit.MINUTES)
                    .flatMap { calendarApi.getCalendar() }

}

interface CalendarManager {

    fun getCalendar(): Observable<Ical>

}

val calendarManagerModule = Kodein.Module {
    bind<CalendarManager>() with provider {
        CalendarManagerImpl(
                calendarApi = instance()
        )
    }
}