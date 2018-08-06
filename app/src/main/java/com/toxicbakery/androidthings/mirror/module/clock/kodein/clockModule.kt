package com.toxicbakery.androidthings.mirror.module.clock.kodein

import com.github.salomonbrys.kodein.Kodein
import com.toxicbakery.androidthings.mirror.module.clock.manager.clockManagerModule
import com.toxicbakery.androidthings.mirror.module.clock.ui.presenter.clockViewPresenterModule

val clockModule = Kodein.Module {
    import(clockManagerModule)
    import(clockViewPresenterModule)
}