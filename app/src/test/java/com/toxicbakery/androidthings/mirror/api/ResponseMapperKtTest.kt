package com.toxicbakery.androidthings.mirror.api

import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Response

class ResponseMapperKtTest {

    @Test
    fun responseMapperSuccess() {
        assertTrue(responseMapper(Response.success(true)))
    }

    @Test
    fun responseMapperUnsuccessful() {
        try {
            responseMapper(Response.error<String>(
                    404,
                    ResponseBody.create(MediaType.parse("application/text"), "")))
        } catch (e: ResponseException) {
            assertEquals(404, e.httpCode)
        }
    }

}