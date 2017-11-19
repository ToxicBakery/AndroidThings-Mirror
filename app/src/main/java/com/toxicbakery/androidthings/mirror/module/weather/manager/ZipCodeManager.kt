package com.toxicbakery.androidthings.mirror.module.weather.manager

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.toxicbakery.androidthings.mirror.module.weather.model.ZipCode
import com.toxicbakery.androidthings.mirror.module.weather.store.ZipCodeStore
import com.toxicbakery.androidthings.mirror.module.weather.store.zipCodeStoreModule
import io.reactivex.Observable

class ZipCodeManagerImpl(
        private val zipCodeStore: ZipCodeStore
) : ZipCodeManager {

    override fun getZipCode(): Observable<ZipCode> = zipCodeStore.zipCodeObservable

    override fun updateZipCode(zipCode: ZipCode) = zipCodeStore.saveZipCode(zipCode)

}

interface ZipCodeManager {

    fun getZipCode(): Observable<ZipCode>

    fun updateZipCode(zipCode: ZipCode)

}

val zipCodeManagerModule = Kodein.Module {
    import(zipCodeStoreModule)
    bind<ZipCodeManager>() with provider { ZipCodeManagerImpl(instance()) }
}

