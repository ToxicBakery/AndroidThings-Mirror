package com.toxicbakery.androidthings.mirror.util

import android.support.annotation.IdRes
import android.support.v7.widget.RecyclerView
import android.view.View

fun <T : View> RecyclerView.ViewHolder.bind(@IdRes id: Int): Lazy<T> = lazy { itemView.findViewById<T>(id) }