package com.toxicbakery.androidthings.mirror

import com.github.salomonbrys.kodein.Kodein.Module
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.singleton
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level

val httpLoggingInterceptorModule = Module {
    bind<Interceptor>(HTTP_LOGGING_INTERCEPTOR) with singleton {
        HttpLoggingInterceptor().setLevel(Level.BODY)
    }
}

const val HTTP_LOGGING_INTERCEPTOR = "HTTP_LOGGING_INTERCEPTOR"