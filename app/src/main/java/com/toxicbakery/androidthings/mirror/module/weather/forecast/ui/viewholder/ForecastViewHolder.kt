package com.toxicbakery.androidthings.mirror.module.weather.forecast.ui.viewholder

import android.support.annotation.IdRes
import android.view.View
import com.toxicbakery.androidthings.mirror.R
import com.toxicbakery.androidthings.mirror.module.weather.forecast.model.Forecast
import com.toxicbakery.androidthings.mirror.module.weather.forecast.model.ForecastRow
import com.toxicbakery.androidthings.mirror.ui.viewholder.ViewHolder
import java.text.SimpleDateFormat
import java.util.*

class ForecastViewHolder(
        override val rootView: View
) : ViewHolder<Forecast>() {

    private val forecastDayOne: ForecastDayViewHolder by bindForecatsDayViewHolder(R.id.first)
    private val forecastDayTwo: ForecastDayViewHolder by bindForecatsDayViewHolder(R.id.second)
    private val forecastDayThree: ForecastDayViewHolder by bindForecatsDayViewHolder(R.id.third)

    override fun bind(value: Forecast) {
        val forecastGroupedByDays = value.list
                .groupBy { SimpleDateFormat("YYYYD", Locale.ENGLISH).format(Date(it.dt * 1000)).toInt() }
                .toSortedMap()
                .map(Map.Entry<Int, List<ForecastRow>>::value)

        println(forecastGroupedByDays)
        forecastGroupedByDays.getOrNull(0)?.let { forecastDayOne.bind(it) }
        forecastGroupedByDays.getOrNull(1)?.let { forecastDayTwo.bind(it) }
        forecastGroupedByDays.getOrNull(2)?.let { forecastDayThree.bind(it) }
    }

    protected fun bindForecatsDayViewHolder(@IdRes id: Int): Lazy<ForecastDayViewHolder> =
            lazy { ForecastDayViewHolder(rootView.findViewById<View>(id)) }

}