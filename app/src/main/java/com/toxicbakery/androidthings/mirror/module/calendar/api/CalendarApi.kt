package com.toxicbakery.androidthings.mirror.module.calendar.api

import android.content.Context
import com.github.salomonbrys.kodein.*
import com.toxicbakery.androidthings.mirror.R
import com.toxicbakery.androidthings.mirror.api.responseMapper
import com.toxicbakery.library.ical.Ical
import com.toxicbakery.library.ical.IcalReader
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class CalendarApiImpl(
        private val okHttpClient: OkHttpClient,
        private val calendarUrl: String
) : CalendarApi {

    private val icalReader = IcalReader()

    override fun getCalendar(): Observable<Ical> = Observable
            .fromCallable {
                Request.Builder()
                        .url(calendarUrl)
                        .build()
                        .let { okHttpClient.newCall(it).execute() }
                        .let(::responseMapper)
                        .byteStream()
                        .bufferedReader()
                        .let { icalReader.readIcal(it) }
            }
            .subscribeOn(Schedulers.io())

}

interface CalendarApi {

    fun getCalendar(): Observable<Ical>

}

val calendarApiModule = Kodein.Module {
    bind<String>("CALENDAR_ICAL_URL") with singleton {
        instance<Context>().getString(R.string.calendar_ical_url)
    }
    bind<OkHttpClient>() with singleton {
        OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(instance()))
                .connectTimeout(30L, TimeUnit.SECONDS)
                .readTimeout(60L, TimeUnit.SECONDS)
                .writeTimeout(30L, TimeUnit.SECONDS)
                .build()
    }
    bind<CalendarApi>() with provider {
        CalendarApiImpl(instance(), instance("CALENDAR_ICAL_URL"))
    }
}