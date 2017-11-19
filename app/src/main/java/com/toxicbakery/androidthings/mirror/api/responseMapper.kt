package com.toxicbakery.androidthings.mirror.api

import retrofit2.Response

fun <T> responseMapper(response: Response<T>): T =
        if (response.isSuccessful) response.body()!!
        else throw ResponseException(response.code())