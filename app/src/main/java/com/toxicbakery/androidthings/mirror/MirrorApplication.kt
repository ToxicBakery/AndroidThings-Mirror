package com.toxicbakery.androidthings.mirror

import android.app.Application
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinAware
import com.github.salomonbrys.kodein.android.androidActivityScope
import com.github.salomonbrys.kodein.lazy
import com.google.android.things.device.TimeManager
import com.toxicbakery.androidthings.mirror.util.isAndroidThings


class MirrorApplication : Application(), KodeinAware {

    override val kodein by Kodein.lazy {
        import(loggingModule)
    }

    override fun onCreate() {
        super.onCreate()
        if (isAndroidThings) TimeManager().setTimeZone("America/New_York")
        registerActivityLifecycleCallbacks(androidActivityScope.lifecycleManager)
        prepareApplication()
    }

}