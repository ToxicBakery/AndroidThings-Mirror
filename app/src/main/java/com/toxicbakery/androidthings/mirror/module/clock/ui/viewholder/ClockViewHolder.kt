package com.toxicbakery.androidthings.mirror.module.clock.ui.viewholder

import android.view.View
import android.widget.TextView
import com.toxicbakery.androidthings.mirror.R
import com.toxicbakery.androidthings.mirror.ui.viewholder.ViewHolder
import java.text.SimpleDateFormat
import java.util.*

class ClockViewHolder(
        override val rootView: View
) : ViewHolder<Date>() {

    private val dateTextView: TextView by bind(R.id.date)
    private val meridiemTextView: TextView by bind(R.id.meridiem)
    private val timeTextView: TextView by bind(R.id.time)

    override fun bind(value: Date) {
        setDate(value)
        setMeridiem(value)
        setTime(value)
    }

    internal fun setDate(date: Date) {
        dateTextView.text = dateFormatter("d").format(Date())
                .let { dayOfMonth ->
                    when {
                        dayOfMonth.endsWith("1") && dayOfMonth != "11" -> dateFormatter(DATE_ST)
                        dayOfMonth.endsWith("2") && dayOfMonth != "12" -> dateFormatter(DATE_ND)
                        dayOfMonth.endsWith("3") && dayOfMonth != "13" -> dateFormatter(DATE_RD)
                        else -> dateFormatter(DATE_TH)
                    }
                }
                .format(date)
    }

    internal fun setMeridiem(date: Date) {
        meridiemTextView.text = dateFormatter("a").format(date)
    }

    internal fun setTime(date: Date) {
        timeTextView.text = dateFormatter("h:mm").format(date)
    }

    companion object {
        private const val DATE_ST = "EEE, MMM d'st', yyyy"
        private const val DATE_ND = "EEE, MMM d'nd', yyyy"
        private const val DATE_RD = "EEE, MMM d'rd', yyyy"
        private const val DATE_TH = "EEE, MMM d'th', yyyy"

        fun dateFormatter(format: String, locale: Locale = Locale.ENGLISH) =
                SimpleDateFormat(format, locale)
    }

}