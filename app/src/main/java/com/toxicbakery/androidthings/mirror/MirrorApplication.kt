package com.toxicbakery.androidthings.mirror

import android.app.Application
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinAware
import com.github.salomonbrys.kodein.android.androidActivityScope
import com.github.salomonbrys.kodein.lazy

class MirrorApplication : Application(), KodeinAware {

    override val kodein by Kodein.lazy {
        import(loggingModule)
    }

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(androidActivityScope.lifecycleManager)
        prepareApplication()
    }

}