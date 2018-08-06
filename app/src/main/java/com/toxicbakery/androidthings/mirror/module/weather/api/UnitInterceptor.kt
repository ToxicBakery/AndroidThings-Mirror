package com.toxicbakery.androidthings.mirror.module.weather.api

import android.support.annotation.StringDef
import com.toxicbakery.androidthings.mirror.api.okhttp.UrlInterceptor
import okhttp3.HttpUrl
import timber.log.Timber

class UnitInterceptor(
        @WeatherUnit private val unit: String
) : UrlInterceptor() {

    override fun intercept(urlBuilder: HttpUrl.Builder) {
        when (unit) {
            UNIT_DEFAULT -> Timber.d("Using default units")
            else -> urlBuilder.addQueryParameter("units", unit)
        }
    }

    companion object {
        const val UNIT_IMPERIAL = "imperial"
        const val UNIT_METRIC = "metric"
        const val UNIT_DEFAULT = "default"
    }

}

@StringDef(
        UnitInterceptor.UNIT_IMPERIAL,
        UnitInterceptor.UNIT_METRIC,
        UnitInterceptor.UNIT_DEFAULT
)
@Retention(AnnotationRetention.SOURCE)
annotation class WeatherUnit