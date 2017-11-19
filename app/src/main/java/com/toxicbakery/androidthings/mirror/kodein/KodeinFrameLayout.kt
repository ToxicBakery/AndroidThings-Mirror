package com.toxicbakery.androidthings.mirror.kodein

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinInjector
import com.toxicbakery.androidthings.mirror.util.isNotInEditMode

open class KodeinFrameLayout @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), AndroidViewInjector {

    final override val injector = KodeinInjector()
    final override val kodeinComponent = super.kodeinComponent
    final override val kodeinScope = super.kodeinScope

    final override fun initializeInjector() = super.initializeInjector()

    @SuppressLint("MissingSuperCall")
    final override fun destroyInjector() = super.destroyInjector()

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (isNotInEditMode) {
            initializeInjector()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (isNotInEditMode) {
            destroyInjector()
        }
    }

}