package com.toxicbakery.androidthings.mirror.module.weather.forecast.ui.viewholder

import android.content.res.Resources
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.toxicbakery.androidthings.mirror.BuildConfig
import com.toxicbakery.androidthings.mirror.R
import com.toxicbakery.androidthings.mirror.module.weather.forecast.model.ForecastRow
import com.toxicbakery.androidthings.mirror.module.weather.model.Weather
import com.toxicbakery.androidthings.mirror.ui.viewholder.ViewHolder
import com.toxicbakery.androidthings.mirror.util.round
import java.text.SimpleDateFormat
import java.util.*

class ForecastDayViewHolder(
        override val rootView: View
) : ViewHolder<List<ForecastRow>>() {

    private val dayOfWeekTextView: TextView by bind(R.id.day_of_week)
    private val iconImageView: ImageView by bind(R.id.icon)
    private val temperatureHighTextView: TextView by bind(R.id.temperature_high)
    private val temperatureLowTextView: TextView by bind(R.id.temperature_low)
    private val resources: Resources = rootView.resources

    override fun bind(value: List<ForecastRow>) {
        dayOfWeekTextView.text =
                value.first()
                        .let { Date(it.dt * 1000) }
                        .let { SimpleDateFormat("EEE", Locale.ENGLISH).format(it) }

        // Get the worst weather condition for the day by grouping on day/night then sorting conditions
        // preferring day icons.
        iconImageView.setImageResource(
                value.flatMap { it.weather }
                        .sortedBy(Weather::icon)
                        .takeLast(2)
                        .groupBy { if (it.icon.endsWith("d")) "day" else "night" }
                        .let { it.getOrElse("day", { it["night"] }) }
                        ?.last()
                        ?.let {
                            resources.getIdentifier(
                                    "weather_${it.icon}", "drawable", BuildConfig.APPLICATION_ID)
                        } ?: 0)

        temperatureLowTextView.text =
                value.sortedBy { it.main.temp_min }
                        .first()
                        .main.temp_min.round().toString()
                        .let { resources.getString(R.string.weather_temperature_with_degree, it) }

        temperatureHighTextView.text =
                value.sortedBy { it.main.temp_max }
                        .last()
                        .main.temp_max.round().toString()
                        .let { resources.getString(R.string.weather_temperature_with_degree, it) }
    }

}