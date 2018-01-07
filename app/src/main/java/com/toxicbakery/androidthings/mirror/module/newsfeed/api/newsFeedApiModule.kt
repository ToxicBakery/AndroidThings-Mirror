package com.toxicbakery.androidthings.mirror.module.newsfeed.api

import android.content.Context
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.toxicbakery.androidthings.mirror.R
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit

val newsFeedApiModule = Kodein.Module {
    bind<OkHttpClient>() with singleton {
        OkHttpClient.Builder()
                .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(instance()))
                .connectTimeout(30L, TimeUnit.SECONDS)
                .readTimeout(30L, TimeUnit.SECONDS)
                .writeTimeout(30L, TimeUnit.SECONDS)
                .build()
    }
    bind<Retrofit>() with singleton {
        Retrofit.Builder()
                .client(instance())
                .baseUrl(instance<Context>().getString(R.string.news_feed_url))
                .addConverterFactory(SimpleXmlConverterFactory.createNonStrict())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .build()
    }
}