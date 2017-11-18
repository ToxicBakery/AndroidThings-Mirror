package com.toxicbakery.androidthings.mirror.ui.presenter

import com.toxicbakery.androidthings.mirror.ui.viewholder.ViewHolder

interface Presenter<in V : ViewHolder<*>> {

    fun bindViewHolder(viewHolder: V)

    fun unbindViewHolder()

}