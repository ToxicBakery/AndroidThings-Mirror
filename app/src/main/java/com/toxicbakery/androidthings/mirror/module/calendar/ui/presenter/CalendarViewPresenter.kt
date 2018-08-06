package com.toxicbakery.androidthings.mirror.module.calendar.ui.presenter

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.toxicbakery.androidthings.mirror.module.calendar.manager.CalendarManager
import com.toxicbakery.androidthings.mirror.module.calendar.ui.viewholder.CalendarViewHolder
import com.toxicbakery.androidthings.mirror.ui.presenter.Presenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import net.fortuna.ical4j.filter.Filter
import net.fortuna.ical4j.filter.PeriodRule
import net.fortuna.ical4j.model.Component
import net.fortuna.ical4j.model.Date
import net.fortuna.ical4j.model.DateTime
import net.fortuna.ical4j.model.Period
import net.fortuna.ical4j.model.component.VEvent
import timber.log.Timber
import java.util.*

internal class CalendarViewPresenterImpl(
        private val calendarManager: CalendarManager
) : CalendarViewPresenter {

    private val javaCalendar = Calendar.getInstance()

    private var subscriptions = CompositeDisposable()

    override fun bindViewHolder(viewHolder: CalendarViewHolder) {
        subscriptions.addAll(
                calendarManager.calendar
                        .map { calendar ->
                            val startDate = Date()
                            javaCalendar.time = startDate
                            javaCalendar.add(Calendar.MONTH, 1)
                            val calEndDate = DateTime(javaCalendar.time)
                            val period = Period(DateTime(Date()), calEndDate)
                            Filter(arrayOf(PeriodRule<VEvent>(period)), Filter.MATCH_ALL)
                                    .filter(calendar.getComponents(Component.VEVENT))
                                    .toList()
                                    .flatMap { event ->
                                        event.calculateRecurrenceSet(period)
                                                .map { period -> period to event }
                                    }
                                    .sortedBy { (period, _) -> period.start.time }
                                    .take(6)
                        }
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { periodEventList -> viewHolder.bind(periodEventList) },
                                { Timber.e(it, "Failed to bind view holder") }))
    }

    override fun unbindViewHolder() {
        subscriptions.clear()
    }

}

interface CalendarViewPresenter : Presenter<CalendarViewHolder>

val calendarViewPresenterModule = Kodein.Module {
    bind<CalendarViewPresenter>() with provider {
        CalendarViewPresenterImpl(
                calendarManager = instance()
        )
    }
}