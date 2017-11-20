package com.toxicbakery.androidthings.mirror.module.calendar.api

import android.content.Context
import com.github.salomonbrys.kodein.*
import com.google.gson.Gson
import com.toxicbakery.androidthings.mirror.R
import com.toxicbakery.androidthings.mirror.api.ResponseException
import com.toxicbakery.library.ical.Ical
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class CalendarApiImpl(
        private val okHttpClient: OkHttpClient,
        private val calendarUrl: String,
        private val gson: Gson
) : CalendarApi {

    override fun getCalendar(): Observable<Ical> =
            Observable.create<Ical> { emitter: ObservableEmitter<Ical> ->
                try {
                    Request.Builder()
                            .url(calendarUrl)
                            .build()
                            .let { okHttpClient.newCall(it).execute() }
                            .let {
                                if (it.isSuccessful) it.body()!!
                                else throw ResponseException(it.code())
                            }
                            .let { gson.fromJson(it.string(), Ical::class.java) }
                            .also {
                                emitter.onNext(it)
                                emitter.onComplete()
                            }
                } catch (e: Exception) {
                    emitter.onError(e)
                }
            }

}

interface CalendarApi {

    fun getCalendar(): Observable<Ical>

}

val calendarApiModule = Kodein.Module {
    bind<Gson>() with singleton { Gson() }
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
        CalendarApiImpl(instance(), instance("CALENDAR_ICAL_URL"), instance())
    }
}