package com.toxicbakery.androidthings.mirror.module.calendar.ui.presenter

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.toxicbakery.androidthings.mirror.module.calendar.manager.CalendarManager
import com.toxicbakery.androidthings.mirror.module.calendar.ui.viewholder.CalendarViewHolder
import com.toxicbakery.androidthings.mirror.ui.presenter.Presenter
import com.toxicbakery.library.ical.Block
import com.toxicbakery.library.ical.Ical
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import java.util.*

internal class CalendarViewPresenterImpl(
        private val calendarManager: CalendarManager
) : CalendarViewPresenter {

    private var subscriptions = CompositeDisposable()

    override fun bindViewHolder(viewHolder: CalendarViewHolder) {
        subscriptions.addAll(
                calendarManager.getCalendar()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { updateViewHolder(viewHolder, it) },
                                { Timber.e(it, "Failed to bind view holder") }))
    }

    override fun unbindViewHolder() {
        subscriptions.clear()
    }

    private fun updateViewHolder(viewHolder: CalendarViewHolder, ical: Ical) =
            ical.blocks
                    .take(5)
                    .filter { block: Block -> block.startDateInTheFuture }
                    .let(viewHolder::bind)

    private val Block.startDateInTheFuture: Boolean
        get() = readStartDate().after(Date())

}

interface CalendarViewPresenter : Presenter<CalendarViewHolder>

val calendarViewPresenterModule = Kodein.Module {
    bind<CalendarViewPresenter>() with provider {
        CalendarViewPresenterImpl(
                calendarManager = instance()
        )
    }
}