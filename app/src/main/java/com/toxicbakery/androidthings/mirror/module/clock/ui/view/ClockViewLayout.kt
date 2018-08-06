package com.toxicbakery.androidthings.mirror.module.clock.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import com.toxicbakery.androidthings.mirror.R
import com.toxicbakery.androidthings.mirror.module.clock.kodein.clockModule
import com.toxicbakery.androidthings.mirror.module.clock.ui.presenter.ClockViewPresenter
import com.toxicbakery.androidthings.mirror.module.clock.ui.viewholder.ClockViewHolder
import com.toxicbakery.androidthings.mirror.ui.view.BaseMvpKodeinFrameLayout

class ClockViewLayout @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : BaseMvpKodeinFrameLayout<ClockViewHolder, ClockViewPresenter>(context, attrs, defStyleAttr) {

    override fun provideOverridingModule() = Kodein.Module {
        import(clockModule)
    }

    override val presenter: ClockViewPresenter by injector.instance()

    override val viewHolder: ClockViewHolder = LayoutInflater.from(context)
            .inflate(R.layout.module_clock, this, true)
            .let(::ClockViewHolder)

}