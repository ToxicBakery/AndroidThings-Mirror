package com.toxicbakery.androidthings.mirror.module.calendar.api

import android.content.Context
import com.github.salomonbrys.kodein.*
import com.toxicbakery.androidthings.mirror.R
import com.toxicbakery.androidthings.mirror.api.responseMapper
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import net.fortuna.ical4j.data.CalendarBuilder
import net.fortuna.ical4j.model.Calendar
import okhttp3.OkHttpClient
import okhttp3.Request

class CalendarApiImpl(
        private val okHttpClient: OkHttpClient,
        private val calendarUrl: String
) : CalendarApi {

    private val calendarRequest: Request
        get() = Request.Builder()
                .url(calendarUrl)
                .build()

    override val calendar: Observable<Calendar>
        get() = Observable.just(calendarRequest)
                .map { request -> okHttpClient.newCall(request).execute() }
                .map(::responseMapper)
                .map { response -> response.byteStream().bufferedReader() }
                .map { reader -> CalendarBuilder().build(reader) }
                .subscribeOn(Schedulers.io())

}

interface CalendarApi {

    val calendar: Observable<Calendar>

}

val calendarApiModule = Kodein.Module {
    bind<String>("CALENDAR_ICAL_URL") with singleton {
        instance<Context>().getString(R.string.calendar_ical_url)
    }
    bind<CalendarApi>() with provider {
        CalendarApiImpl(
                okHttpClient = instance<OkHttpClient.Builder>().build(),
                calendarUrl = instance("CALENDAR_ICAL_URL")
        )
    }
}