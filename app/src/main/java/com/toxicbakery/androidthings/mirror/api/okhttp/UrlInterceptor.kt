package com.toxicbakery.androidthings.mirror.api.okhttp

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

abstract class UrlInterceptor : Interceptor {

    abstract fun intercept(urlBuilder: HttpUrl.Builder)

    override fun intercept(chain: Interceptor.Chain): Response {
        chain.request().let { originalRequest: Request ->
            val url = originalRequest
                    .url()
                    .newBuilder()
                    .also { intercept(it) }
                    .build()

            return originalRequest
                    .newBuilder()
                    .url(url)
                    .build()
                    .let { newChain -> chain.proceed(newChain) }
        }
    }

}