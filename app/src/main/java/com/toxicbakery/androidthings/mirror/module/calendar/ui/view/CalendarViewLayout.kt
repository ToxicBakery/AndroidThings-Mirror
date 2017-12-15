package com.toxicbakery.androidthings.mirror.module.calendar.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import com.toxicbakery.androidthings.mirror.R
import com.toxicbakery.androidthings.mirror.module.calendar.kodein.calendarKodein
import com.toxicbakery.androidthings.mirror.module.calendar.ui.adapter.CalendarAdapter
import com.toxicbakery.androidthings.mirror.module.calendar.ui.presenter.CalendarViewPresenter
import com.toxicbakery.androidthings.mirror.module.calendar.ui.viewholder.CalendarViewHolder
import com.toxicbakery.androidthings.mirror.ui.view.BaseMvpKodeinFrameLayout

class CalendarViewLayout @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : BaseMvpKodeinFrameLayout<CalendarViewHolder, CalendarViewPresenter>(context, attrs, defStyleAttr) {

    private val adapter = CalendarAdapter()

    override fun provideOverridingKodein() = Kodein {
        extend(calendarKodein)
    }

    override val presenter: CalendarViewPresenter by injector.instance()

    override val viewHolder: CalendarViewHolder = LayoutInflater.from(context)
            .inflate(R.layout.module_calendar, this, true)
            .let { CalendarViewHolder(it, adapter) }
}