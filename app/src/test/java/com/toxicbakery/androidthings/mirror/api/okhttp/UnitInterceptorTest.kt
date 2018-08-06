package com.toxicbakery.androidthings.mirror.api.okhttp

import com.toxicbakery.androidthings.mirror.module.weather.api.UnitInterceptor
import okhttp3.HttpUrl
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class UnitInterceptorTest {

    @Test
    fun intercept() {
        HttpUrl.parse("https://google.com/")!!
                .newBuilder()
                .also { UnitInterceptor(UnitInterceptor.UNIT_METRIC).intercept(it) }
                .let(HttpUrl.Builder::build)
                .queryParameter("units")
                .let { assertEquals(UnitInterceptor.UNIT_METRIC, it) }
    }

    @Test
    fun interceptNoModification() {
        HttpUrl.parse("https://google.com/")!!
                .newBuilder()
                .also { UnitInterceptor(UnitInterceptor.UNIT_DEFAULT).intercept(it) }
                .let(HttpUrl.Builder::build)
                .queryParameter("units")
                .let(::assertNull)
    }

}