package com.toxicbakery.androidthings.mirror.module.news.feed.manager

import com.github.salomonbrys.kodein.*
import com.toxicbakery.androidthings.mirror.api.responseMapper
import com.toxicbakery.androidthings.mirror.module.news.api.RssNewsFeedApi
import com.toxicbakery.androidthings.mirror.module.news.model.Feed
import io.reactivex.Observable
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class NewsFeedManagerImpl(
        private val newsFeedApi: RssNewsFeedApi
) : NewsFeedManager {

    private val update: Observable<Feed> =
            Observable.interval(0, 10, TimeUnit.MINUTES)
                    .flatMap { newsFeedApi.feed().onErrorResumeNext(Observable.empty()) }
                    .map { responseMapper(it) }
                    .share()

    override fun getNewsFeed(): Observable<Feed> = update

}

interface NewsFeedManager {

    fun getNewsFeed(): Observable<Feed>

}

val newsFeedManagerModule = Kodein.Module {
    bind<RssNewsFeedApi>() with provider { instance<Retrofit>().create(RssNewsFeedApi::class.java) }
    bind<NewsFeedManager>() with singleton {
        NewsFeedManagerImpl(
                newsFeedApi = instance()
        )
    }
}


