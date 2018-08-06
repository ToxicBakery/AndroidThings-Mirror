package com.toxicbakery.androidthings.mirror.module.weather.api

import com.toxicbakery.androidthings.mirror.api.okhttp.UrlInterceptor
import okhttp3.HttpUrl

class ApiKeyInterceptor(
        private val apiKey: String
) : UrlInterceptor() {

    override fun intercept(urlBuilder: HttpUrl.Builder) {
        urlBuilder.addQueryParameter("APPID", apiKey)
    }

}