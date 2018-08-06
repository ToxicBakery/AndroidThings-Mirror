package com.toxicbakery.androidthings.mirror.api.okhttp

import com.toxicbakery.androidthings.mirror.module.weather.api.ApiKeyInterceptor
import okhttp3.HttpUrl
import org.junit.Assert.assertEquals
import org.junit.Test

class ApiKeyInterceptorTest {

    @Test
    fun intercept() {
        HttpUrl.parse("https://google.com/")!!
                .newBuilder()
                .also { ApiKeyInterceptor("key").intercept(it) }
                .let(HttpUrl.Builder::build)
                .queryParameter("APPID")
                .let { assertEquals("key", it) }
    }

}