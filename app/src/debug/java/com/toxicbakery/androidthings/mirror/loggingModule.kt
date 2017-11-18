package com.toxicbakery.androidthings.mirror

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.provider
import okhttp3.logging.HttpLoggingInterceptor

val loggingModule = Kodein.Module {
    bind<HttpLoggingInterceptor.Level>() with provider { HttpLoggingInterceptor.Level.BODY }
}