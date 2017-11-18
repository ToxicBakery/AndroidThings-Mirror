package com.toxicbakery.androidthings.mirror

import android.app.Application
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinAware
import com.github.salomonbrys.kodein.android.androidActivityScope
import com.github.salomonbrys.kodein.lazy
import com.toxicbakery.androidthings.mirror.job.dispatcherJobServiceModule
import com.toxicbakery.androidthings.mirror.module.clock.kodein.moduleClockKodein
import com.toxicbakery.androidthings.mirror.module.currentweather.kodein.moduleCurrentWeatherKodein

class MirrorApplication : Application(), KodeinAware {

    override val kodein by Kodein.lazy {
        import(loggingModule)
        import(dispatcherJobServiceModule)

        // View Modules
        extend(moduleClockKodein)
        extend(moduleCurrentWeatherKodein)
    }

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(androidActivityScope.lifecycleManager)
        prepareApplication()
    }

}