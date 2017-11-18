package com.toxicbakery.androidthings.mirror.module.currentweather.store

import android.content.Context
import com.github.salomonbrys.kodein.*
import com.toxicbakery.androidthings.mirror.R
import com.toxicbakery.androidthings.mirror.module.currentweather.model.ZipCode
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

class ZipCodeStoreImpl(
        private val subject: Subject<ZipCode>
) : ZipCodeStore {

    override val zipCodeObservable: Observable<ZipCode>
        get() = subject

    override fun saveZipCode(zipCode: ZipCode) = subject.onNext(zipCode)

}

interface ZipCodeStore {

    val zipCodeObservable: Observable<ZipCode>

    fun saveZipCode(zipCode: ZipCode)

}

val zipCodeStoreModule = Kodein.Module {
    bind<ZipCode>("INITIAL_ZIP_CODE") with provider {
        instance<Context>().getString(R.string.zip_code_and_country_code)
                .split(",")
                .let { ZipCode(it[0].toInt(), it[1]) }
    }
    bind<Subject<ZipCode>>("SUBJECT_ZIP_CODE") with singleton {
        BehaviorSubject.create<ZipCode>().apply { onNext(instance("INITIAL_ZIP_CODE")) }
    }
    bind<ZipCodeStore>() with provider { ZipCodeStoreImpl(instance("SUBJECT_ZIP_CODE")) }
}