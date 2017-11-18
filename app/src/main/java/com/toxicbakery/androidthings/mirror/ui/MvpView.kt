package com.toxicbakery.androidthings.mirror.ui

import com.toxicbakery.androidthings.mirror.ui.presenter.Presenter
import com.toxicbakery.androidthings.mirror.ui.viewholder.ViewHolder

interface MvpView<V : ViewHolder<*>, out P : Presenter<V>> {

    val presenter: P

    val viewHolder: V

}