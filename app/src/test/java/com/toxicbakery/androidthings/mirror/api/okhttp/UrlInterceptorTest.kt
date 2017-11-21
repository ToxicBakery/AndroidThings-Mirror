package com.toxicbakery.androidthings.mirror.api.okhttp

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import okhttp3.*
import org.junit.Assert.assertEquals
import org.junit.Test

class UrlInterceptorTest {

    @Test
    fun intercept() {
        val request = Request.Builder()
                .url("https://google.com/")
                .build()

        val chain: Interceptor.Chain = mock()
        whenever(chain.request()).thenReturn(request)
        whenever(chain.proceed(any())).thenReturn(Response.Builder()
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .request(request)
                .message("ok")
                .build())

        object : UrlInterceptor() {
            override fun intercept(urlBuilder: HttpUrl.Builder) {
                assertEquals("google.com", urlBuilder.build().host())
            }
        }.intercept(chain)
    }

}