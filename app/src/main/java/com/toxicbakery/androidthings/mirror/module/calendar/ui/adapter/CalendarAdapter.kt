package com.toxicbakery.androidthings.mirror.module.calendar.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.toxicbakery.androidthings.mirror.R
import com.toxicbakery.androidthings.mirror.util.autoNotify
import net.fortuna.ical4j.model.Period
import net.fortuna.ical4j.model.component.VEvent
import kotlin.properties.Delegates

class CalendarAdapter : RecyclerView.Adapter<CalendarRowViewHolder>() {

    private val Pair<Period, VEvent>.id: String
        get() = "${first.start.time}-${first.end.time}-${second.uid.value}"

    private var events: List<Pair<Period, VEvent>> by Delegates.observable(emptyList()) { _, old, new ->
        autoNotify(old, new) { left, right -> left.id == right.id }
    }

    override fun onBindViewHolder(holder: CalendarRowViewHolder, position: Int) =
            getItem(position).let { (period, event) -> holder.bind(period, event) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarRowViewHolder =
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.calendar_row, parent, false)
                    .let(::CalendarRowViewHolder)

    override fun getItemCount(): Int = events.size

    fun updateEvents(events: List<Pair<Period, VEvent>>) {
        this.events = events
    }

    private fun getItem(position: Int): Pair<Period, VEvent> = events[position]

}