package com.toxicbakery.androidthings.mirror.module.newsfeed.kodein

import com.github.salomonbrys.kodein.Kodein
import com.toxicbakery.androidthings.mirror.module.newsfeed.api.newsFeedApiModule
import com.toxicbakery.androidthings.mirror.module.newsfeed.manager.newsFeedManagerModule
import com.toxicbakery.androidthings.mirror.module.newsfeed.ui.presenter.newsFeedPresenterModule

val newsFeedKodein = Kodein {
    import(newsFeedApiModule)
    import(newsFeedPresenterModule)
    import(newsFeedManagerModule)
}