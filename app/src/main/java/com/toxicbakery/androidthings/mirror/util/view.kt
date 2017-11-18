package com.toxicbakery.androidthings.mirror.util

import android.view.View

val View.isNotInEditMode: Boolean
    get() = !isInEditMode