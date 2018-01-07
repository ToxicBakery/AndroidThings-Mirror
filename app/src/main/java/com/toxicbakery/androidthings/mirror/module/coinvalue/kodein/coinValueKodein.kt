package com.toxicbakery.androidthings.mirror.module.coinvalue.kodein

import com.github.salomonbrys.kodein.Kodein
import com.toxicbakery.androidthings.mirror.module.coinvalue.api.coinValueApiModule
import com.toxicbakery.androidthings.mirror.module.coinvalue.manager.coinValueManagerModule
import com.toxicbakery.androidthings.mirror.module.coinvalue.ui.presenter.coinValuePresenterModule


val coinValueKodein = Kodein {
    import(coinValueApiModule)
    import(coinValuePresenterModule)
    import(coinValueManagerModule)
}