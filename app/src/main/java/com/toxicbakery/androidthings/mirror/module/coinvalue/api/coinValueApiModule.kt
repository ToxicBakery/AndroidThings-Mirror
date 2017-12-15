package com.toxicbakery.androidthings.mirror.module.coinvalue.api

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val COIN_VALUE_BASE_URL: String = "COIN_VALUE_BASE_URL"

val coinValueApiModule = Kodein.Module {
    bind<String>(COIN_VALUE_BASE_URL) with singleton { "https://api.coinmarketcap.com/v1/" }
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
                .baseUrl(instance<String>(COIN_VALUE_BASE_URL))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .build()
    }
}