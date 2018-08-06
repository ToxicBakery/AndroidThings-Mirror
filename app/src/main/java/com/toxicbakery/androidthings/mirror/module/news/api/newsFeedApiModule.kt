package com.toxicbakery.androidthings.mirror.module.news.api

import android.content.Context
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.toxicbakery.androidthings.mirror.R
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

val newsApiModule = Kodein.Module {
    bind<Retrofit>() with singleton {
        Retrofit.Builder()
                .client(instance<OkHttpClient.Builder>().build())
                .baseUrl(instance<Context>().getString(R.string.news_feed_url))
                .addConverterFactory(SimpleXmlConverterFactory.createNonStrict())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .build()
    }
}