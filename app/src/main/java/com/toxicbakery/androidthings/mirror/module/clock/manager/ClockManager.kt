package com.toxicbakery.androidthings.mirror.module.clock.manager

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.provider
import io.reactivex.Observable
import java.util.*
import java.util.concurrent.TimeUnit

class ClockManagerImpl : ClockManager {

    override fun getDateTime(): Observable<Date> =
            Observable.interval(1, TimeUnit.SECONDS)
                    .map { Date() }

}

interface ClockManager {
    fun getDateTime(): Observable<Date>
}

val clockManagerModule = Kodein.Module {
    bind<ClockManager>() with provider { ClockManagerImpl() }
}