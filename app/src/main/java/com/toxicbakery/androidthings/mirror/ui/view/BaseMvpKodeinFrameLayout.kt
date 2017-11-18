package com.toxicbakery.androidthings.mirror.ui.view

import android.content.Context
import android.util.AttributeSet
import com.toxicbakery.androidthings.mirror.kodein.KodeinFrameLayout
import com.toxicbakery.androidthings.mirror.ui.MvpView
import com.toxicbakery.androidthings.mirror.ui.presenter.Presenter
import com.toxicbakery.androidthings.mirror.ui.viewholder.ViewHolder
import com.toxicbakery.androidthings.mirror.util.isNotInEditMode

abstract class BaseMvpKodeinFrameLayout<V : ViewHolder<*>, out P : Presenter<V>>
@JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : KodeinFrameLayout(context, attrs, defStyleAttr), MvpView<V, P> {

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (isNotInEditMode) presenter.bindViewHolder(viewHolder)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (isNotInEditMode) presenter.unbindViewHolder()
    }

}