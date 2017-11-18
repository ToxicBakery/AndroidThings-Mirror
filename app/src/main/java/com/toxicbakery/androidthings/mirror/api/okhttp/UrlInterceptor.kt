package com.toxicbakery.androidthings.mirror.api.okhttp

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

abstract class UrlInterceptor : Interceptor {

    abstract fun intercept(urlBuilder: HttpUrl.Builder)

    override fun intercept(chain: Interceptor.Chain): Response {
        chain.request().let { originalChain: Request ->
            val url = originalChain
                    .url()
                    .newBuilder()
                    .also { intercept(it) }
                    .build()

            return originalChain
                    .newBuilder()
                    .url(url)
                    .build()
                    .let { newChain -> chain.proceed(newChain) }
        }
    }

}