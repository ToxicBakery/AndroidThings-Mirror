package com.toxicbakery.androidthings.mirror.module.weather.api

import android.content.Context
import com.github.salomonbrys.kodein.*
import com.toxicbakery.androidthings.mirror.R
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val WEATHER_API_BASE_URL: String = "WEATHER_API_BASE_URL"
const val WEATHER_API_KEY: String = "WEATHER_API_KEY"
const val WEATHER_API_KEY_INTERCEPTOR: String = "WEATHER_API_KEY_INTERCEPTOR"
const val WEATHER_API_UNIT_INTERCEPTOR: String = "WEATHER_API_UNIT_INTERCEPTOR"
const val WEATHER_API_UNIT: String = "WEATHER_API_UNIT"

val weatherApiModule = Kodein.Module {
    bind<String>(WEATHER_API_BASE_URL) with singleton { instance<Context>().getString(R.string.open_weather_base_url) }
    bind<String>(WEATHER_API_KEY) with singleton { instance<Context>().getString(R.string.open_weather_api_key) }
    bind<String>(WEATHER_API_UNIT) with singleton { UnitInterceptor.UNIT_IMPERIAL }
    bind<Interceptor>(WEATHER_API_KEY_INTERCEPTOR) with provider { ApiKeyInterceptor(instance(WEATHER_API_KEY)) }
    bind<Interceptor>(WEATHER_API_UNIT_INTERCEPTOR) with provider { UnitInterceptor(instance(WEATHER_API_UNIT)) }
    bind<OkHttpClient>() with singleton {
        instance<OkHttpClient.Builder>()
                .addInterceptor(instance(WEATHER_API_KEY_INTERCEPTOR))
                .addInterceptor(instance(WEATHER_API_UNIT_INTERCEPTOR))
                .build()
    }
    bind<Retrofit>() with singleton {
        Retrofit.Builder()
                .client(instance())
                .baseUrl(instance<String>(WEATHER_API_BASE_URL))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .build()
    }
}