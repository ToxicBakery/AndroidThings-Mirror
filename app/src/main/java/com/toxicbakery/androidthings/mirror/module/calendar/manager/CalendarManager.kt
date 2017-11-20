package com.toxicbakery.androidthings.mirror.module.calendar.manager

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.toxicbakery.androidthings.mirror.module.calendar.api.CalendarApi
import com.toxicbakery.androidthings.mirror.module.calendar.store.CalendarStore
import com.toxicbakery.androidthings.mirror.module.calendar.store.calendarStoreModule
import com.toxicbakery.library.ical.Ical
import io.reactivex.Observable
import retrofit2.Retrofit

class CalendarManagerImpl(
        private val calendarApi: CalendarApi,
        private val calendarStore: CalendarStore
) : CalendarManager {

    override fun getCalendar(): Observable<Ical> =
            calendarStore.calendarObservable

    override fun updateCalendar(): Observable<Ical> =
            calendarApi.getCalendar()
                    .doOnNext { calendarStore.saveCalendar(it) }

}

interface CalendarManager {

    fun getCalendar(): Observable<Ical>

    fun updateCalendar(): Observable<Ical>

}

val calendarManagerModule = Kodein.Module {
    import(calendarStoreModule)
    bind<CalendarApi>() with provider { instance<Retrofit>().create(CalendarApi::class.java) }
    bind<CalendarManager>() with provider { CalendarManagerImpl(instance(), instance()) }
}