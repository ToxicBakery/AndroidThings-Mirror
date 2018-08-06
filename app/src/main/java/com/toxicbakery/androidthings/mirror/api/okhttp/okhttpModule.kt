package com.toxicbakery.androidthings.mirror.api.okhttp

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.toxicbakery.androidthings.mirror.HTTP_LOGGING_INTERCEPTOR
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

val okhttpModule = Kodein.Module {
    bind<OkHttpClient.Builder>() with provider {
        OkHttpClient.Builder()
                .connectionPool(ConnectionPool(4, 5, TimeUnit.MINUTES))
                .connectTimeout(30L, TimeUnit.SECONDS)
                .readTimeout(30L, TimeUnit.SECONDS)
                .writeTimeout(30L, TimeUnit.SECONDS)
                .addInterceptor(instance(HTTP_LOGGING_INTERCEPTOR))
    }
}
