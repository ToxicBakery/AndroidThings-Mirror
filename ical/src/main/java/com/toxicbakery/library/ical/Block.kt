package com.toxicbakery.library.ical

import java.text.SimpleDateFormat
import java.util.*

interface Block {
    val type: String
    val values: Map<String, String>

    fun readStartDate(): Date =
            values[VALUE_START_DATE]?.let(timeStampParser::parse)
                    ?: values[VALUE_START_DATE_EXTENDED]?.let(dateParser::parse)
                    ?: Date()

    fun readEndDate(): Date =
            values[VALUE_END_DATE]?.let(timeStampParser::parse)
                    ?: values[VALUE_END_DATE_EXTENDED]?.let(dateParser::parse)
                    ?: Date()

    companion object {
        private const val VALUE_START_DATE = "DTSTART"
        private const val VALUE_START_DATE_EXTENDED = "DTSTART;VALUE=DATE"
        private const val VALUE_END_DATE = "DTEND"
        private const val VALUE_END_DATE_EXTENDED = "DTEND;VALUE=DATE"

        private val dateParser = SimpleDateFormat("yyyyMMdd", Locale.ENGLISH)
        private val timeStampParser = SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'", Locale.ENGLISH)
    }
}