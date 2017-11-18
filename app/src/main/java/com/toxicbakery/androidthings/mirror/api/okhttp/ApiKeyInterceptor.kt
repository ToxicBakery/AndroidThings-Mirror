package com.toxicbakery.androidthings.mirror.api.okhttp

import okhttp3.HttpUrl

class ApiKeyInterceptor(
        private val apiKey: String
) : UrlInterceptor() {

    override fun intercept(urlBuilder: HttpUrl.Builder) {
        urlBuilder.addQueryParameter("APPID", apiKey)
    }

}