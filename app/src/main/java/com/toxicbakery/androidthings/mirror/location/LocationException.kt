package com.toxicbakery.androidthings.mirror.location

class LocationException : Exception {

    constructor(message: String) : super(message)

    constructor(message: String, cause: Exception) : super(message, cause)

}