package com.toxicbakery.androidthings.mirror.module.calendar.store

import com.github.salomonbrys.kodein.*
import com.toxicbakery.library.ical.Ical
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

class CalendarStoreImpl(
        private val subject: Subject<Ical>
) : CalendarStore {

    override val calendarObservable: Observable<Ical>
        get() = subject

    override fun saveCalendar(ical: Ical) = subject.onNext(ical)

}

interface CalendarStore {

    val calendarObservable: Observable<Ical>

    fun saveCalendar(ical: Ical)

}

val calendarStoreModule = Kodein.Module {
    bind<Subject<Ical>>("SUBJECT_CALENDAR") with singleton {
        BehaviorSubject.create<Ical>()
    }
    bind<CalendarStore>() with provider { CalendarStoreImpl(instance("SUBJECT_CALENDAR")) }
}