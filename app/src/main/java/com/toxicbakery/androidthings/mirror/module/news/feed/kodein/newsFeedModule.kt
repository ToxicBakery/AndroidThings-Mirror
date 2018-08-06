package com.toxicbakery.androidthings.mirror.module.news.feed.kodein

import com.github.salomonbrys.kodein.Kodein
import com.toxicbakery.androidthings.mirror.module.news.api.newsApiModule
import com.toxicbakery.androidthings.mirror.module.news.feed.manager.newsFeedManagerModule
import com.toxicbakery.androidthings.mirror.module.news.feed.ui.presenter.newsFeedPresenterModule

val newsFeedModule = Kodein.Module {
    import(newsApiModule)
    import(newsFeedPresenterModule)
    import(newsFeedManagerModule)
}