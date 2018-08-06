package com.toxicbakery.androidthings.mirror.module.weather.kodein

import com.github.salomonbrys.kodein.Kodein
import com.toxicbakery.androidthings.mirror.module.weather.api.weatherApiModule
import com.toxicbakery.androidthings.mirror.module.weather.manager.zipCodeManagerModule

val weatherModule = Kodein.Module {
    import(weatherApiModule)
    import(zipCodeManagerModule)
}