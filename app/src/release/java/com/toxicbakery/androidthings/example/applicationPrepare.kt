package com.toxicbakery.androidthings.mirror

import timber.log.Timber

fun prepareApplication() {
    Timber.plant(Timber.DebugTree())
}
