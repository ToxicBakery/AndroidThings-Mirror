package com.toxicbakery.androidthings.mirror.module.news.api

import com.toxicbakery.androidthings.mirror.module.news.model.Feed
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET

interface RssNewsFeedApi {

    /**
     * Get the RSS news feed.
     */
    @GET("headlines/section/topic/WORLD?ned=us&gl=US&hl=en")
    fun feed(): Observable<Response<Feed>>

}