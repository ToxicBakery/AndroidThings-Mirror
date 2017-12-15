package com.toxicbakery.androidthings.mirror.ui.viewholder

import android.content.Context
import android.support.annotation.IdRes
import android.view.View

abstract class ViewHolder<in V> {
    abstract val rootView: View

    abstract fun bind(value: V)

    protected val context: Context
        get() = rootView.context

    protected fun <T : View> bind(@IdRes id: Int): Lazy<T> = lazy { rootView.findViewById<T>(id) }
}