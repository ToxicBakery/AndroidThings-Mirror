package com.toxicbakery.library.ical

import java.text.SimpleDateFormat
import java.util.*

interface Block {
    val type: String
    val values: Map<String, String>

    fun readStartDate(): Date =
            values[VALUE_START_DATE]?.let(::parseDate)
                    ?: values[VALUE_START_DATE_EXTENDED]?.let(dateParser::parse)
                    ?: Date()

    fun readEndDate(): Date =
            values[VALUE_END_DATE]?.let(::parseDate)
                    ?: values[VALUE_END_DATE_EXTENDED]?.let(dateParser::parse)
                    ?: Date()


    companion object {
        private const val VALUE_START_DATE = "DTSTART"
        private const val VALUE_START_DATE_EXTENDED = "DTSTART;VALUE=DATE"
        private const val VALUE_END_DATE = "DTEND"
        private const val VALUE_END_DATE_EXTENDED = "DTEND;VALUE=DATE"

        private val dateParser = SimpleDateFormat("yyyyMMdd", Locale.ENGLISH)
        private val timeStampParsers = arrayOf(
                SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'", Locale.ENGLISH),
                SimpleDateFormat("yyyyMMdd'T'HHmmss", Locale.ENGLISH).apply {
                    timeZone = TimeZone.getTimeZone("America/New_York")
                }
        )

        fun parseDate(date: String): Date {
            for (parser in timeStampParsers) {
                try {
                    return parser.parse(date)
                } catch (e: Exception) {
                }
            }
            throw Exception("Unable to parse date $date")
        }
    }
}