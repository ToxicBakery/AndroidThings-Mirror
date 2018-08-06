package com.toxicbakery.androidthings.mirror

import android.app.Application
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinAware
import com.github.salomonbrys.kodein.android.androidActivityScope
import com.github.salomonbrys.kodein.lazy
import com.google.android.things.device.TimeManager
import com.toxicbakery.androidthings.mirror.api.okhttp.okhttpModule
import timber.log.Timber

class MirrorApplication : Application(), KodeinAware {

    override val kodein by Kodein.lazy {
        import(httpLoggingInterceptorModule)
        import(okhttpModule)
    }

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(androidActivityScope.lifecycleManager)
        prepareApplication()
        try {
            TimeManager.getInstance()
                    .setAutoTimeEnabled(true)
        } catch (e: NoClassDefFoundError) {
            Timber.e(e, "Unable to set time zone")
        }
        registerActivityLifecycleCallbacks(androidActivityScope.lifecycleManager)

    }

}