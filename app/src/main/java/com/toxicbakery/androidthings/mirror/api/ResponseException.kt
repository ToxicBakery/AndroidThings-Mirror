package com.toxicbakery.androidthings.mirror.api

class ResponseException(
        val httpCode: Int
) : Exception()