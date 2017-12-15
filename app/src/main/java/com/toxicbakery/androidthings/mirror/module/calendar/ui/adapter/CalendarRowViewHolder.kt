package com.toxicbakery.androidthings.mirror.module.calendar.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.toxicbakery.androidthings.mirror.R
import com.toxicbakery.androidthings.mirror.util.bind
import com.toxicbakery.library.ical.Block
import java.text.SimpleDateFormat
import java.util.*

class CalendarRowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val dayOfWeekTextView: TextView by bind(R.id.day_of_week)
    private val monthAndDayTextView: TextView by bind(R.id.month_and_day)
    private val durationTextView: TextView by bind(R.id.duration)
    private val titleTextView: TextView by bind(R.id.title)

    // Sample format 20171118T224627Z
    private val timeStampParser = formatter("yyyyMMdd'T'HHmmss'Z'")
    private val dayOfWeekFormatter = formatter("EEE")
    private val timeFormatter: SimpleDateFormat = formatter("k:mm a")
    private val monthAndDayFormatter = formatter("MMM dd")

    fun bind(block: Block) {
        val startDate = parserDateTime(block.value(VALUE_START_DATE, block.value(VALUE_DATE)))
        val endDate = parserDateTime(block.value(VALUE_END_DATE, block.value(VALUE_DATE)))
        dayOfWeekTextView.text = dayOfWeekFormatter.format(startDate)
        monthAndDayTextView.text = monthAndDayFormatter.format(startDate)
        titleTextView.text = block.value(VALUE_SUMMARY)
        setDuration(startDate, endDate)
    }

    private fun setDuration(startDate: Date, endDate: Date) {
        if (startDate == endDate) {
            durationTextView.setText(R.string.calendar_event_all_day)
        } else {
            val start = timeFormatter.format(startDate)
            val end = timeFormatter.format(endDate)
            durationTextView.text = durationTextView.context
                    .getString(R.string.calendar_event_duration, start, end)
        }
    }

    private fun formatter(format: String): SimpleDateFormat =
            SimpleDateFormat(format, Locale.ENGLISH)

    private fun parserDateTime(dateTime: String): Date =
            if (dateTime.isEmpty()) Date()
            else timeStampParser.parse(dateTime)

    private fun Block.value(name: String, default: String = "") =
            values.getOrDefault(name, default)

    companion object {
        private const val VALUE_DATE = "DTSTAMP"
        private const val VALUE_END_DATE = "DTEND"
        private const val VALUE_START_DATE = "DTSTART"
        private const val VALUE_SUMMARY = "SUMMARY"
    }

}