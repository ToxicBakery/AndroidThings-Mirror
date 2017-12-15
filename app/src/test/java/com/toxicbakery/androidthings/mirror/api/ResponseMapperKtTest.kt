package com.toxicbakery.androidthings.mirror.api

import okhttp3.MediaType
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Response

class ResponseMapperKtTest {

    @Test
    fun responseMapperSuccessRetrofit() {
        assertTrue(responseMapper(Response.success(true)))
    }

    @Test
    fun responseMapperUnsuccessfulRetrofit() {
        try {
            responseMapper(Response.error<String>(
                    404,
                    ResponseBody.create(MediaType.parse("application/text"), "")))
        } catch (e: ResponseException) {
            assertEquals(404, e.httpCode)
        }
    }

    @Test
    fun responseMapperSuccessOkhttp() {
        okhttp3.Response.Builder()
                .body(ResponseBody.create(mediaTypeText, "success"))
                .code(200)
                .message("ok")
                .protocol(Protocol.HTTP_1_1)
                .request(okhttpRequest)
                .build()
                .let(::responseMapper)
                .let { assertEquals("success", it.string()) }
    }

    @Test
    fun responseMapperUnsuccessfulOkhttp() {
        try {
            okhttp3.Response.Builder()
                    .code(404)
                    .message("Not Found")
                    .protocol(Protocol.HTTP_1_1)
                    .request(okhttpRequest)
                    .build()
                    .let(::responseMapper)
        } catch (e: ResponseException) {
            assertEquals(404, e.httpCode)
        }
    }

    companion object {
        private val mediaTypeText = MediaType.parse("application/text")
        private val okhttpRequest = Request.Builder()
                .url("https://google.com/")
                .build()
    }

}