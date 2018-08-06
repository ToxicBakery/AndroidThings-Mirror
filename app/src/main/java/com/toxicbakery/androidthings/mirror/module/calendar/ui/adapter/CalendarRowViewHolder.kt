package com.toxicbakery.androidthings.mirror.module.calendar.ui.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.toxicbakery.androidthings.mirror.R
import com.toxicbakery.androidthings.mirror.util.bind
import net.fortuna.ical4j.model.Period
import net.fortuna.ical4j.model.component.VEvent
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("StringFormatInvalid")
class CalendarRowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val durationTextView: TextView by bind(R.id.duration)
    private val titleTextView: TextView by bind(R.id.title)

    private val timeFormatter: SimpleDateFormat = formatter("hh:mm a")
    private val monthAndDayFormatter = formatter("EEE MMM dd")
    private val allDayFormatter = formatter("EEE MMM dd")

    fun bind(period: Period, event: VEvent) {
        titleTextView.text = event.summary.value
        if (period.duration.days == 0) {
            val monthAndDay = monthAndDayFormatter.format(period.start)
            val hoursStart = timeFormatter.format(period.start)
            val hoursEnd = timeFormatter.format(period.end)
            durationTextView.text = itemView.context.getString(
                    R.string.calendar_event_day_month_day_duration,
                    monthAndDay, hoursStart, hoursEnd)
        } else if (period.duration.days == 1 && period.duration.hours == 0) {
            durationTextView.text = allDayFormatter.format(period.start)
        } else {
            durationTextView.text = itemView.resources.getString(
                    R.string.calendar_event_duration,
                    allDayFormatter.format(period.start),
                    allDayFormatter.format(period.end))
        }
    }

    private fun formatter(format: String): SimpleDateFormat =
            SimpleDateFormat(format, Locale.ENGLISH)

}