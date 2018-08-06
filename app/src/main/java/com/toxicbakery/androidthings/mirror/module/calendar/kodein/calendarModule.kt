package com.toxicbakery.androidthings.mirror.module.calendar.kodein

import com.github.salomonbrys.kodein.Kodein
import com.toxicbakery.androidthings.mirror.module.calendar.api.calendarApiModule
import com.toxicbakery.androidthings.mirror.module.calendar.manager.calendarManagerModule
import com.toxicbakery.androidthings.mirror.module.calendar.ui.presenter.calendarViewPresenterModule

val calendarModule = Kodein.Module {
    import(calendarApiModule)
    import(calendarManagerModule)
    import(calendarViewPresenterModule)
}