package com.toxicbakery.androidthings.mirror.util

import android.content.Context
import android.content.pm.PackageManager

val Context.isAndroidThings: Boolean
    get() = hasSystemFeature(PackageManager.FEATURE_EMBEDDED)

private fun Context.hasSystemFeature(name: String): Boolean =
        packageManager.hasSystemFeature(name)