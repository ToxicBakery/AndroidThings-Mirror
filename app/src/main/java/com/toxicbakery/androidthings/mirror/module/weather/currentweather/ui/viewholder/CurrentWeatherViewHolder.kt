package com.toxicbakery.androidthings.mirror.module.weather.currentweather.ui.viewholder

import android.annotation.SuppressLint
import android.content.res.Resources
import android.icu.text.SimpleDateFormat
import android.location.Geocoder
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.toxicbakery.androidthings.mirror.BuildConfig
import com.toxicbakery.androidthings.mirror.R
import com.toxicbakery.androidthings.mirror.module.weather.currentweather.model.CurrentWeather
import com.toxicbakery.androidthings.mirror.module.weather.model.Coord
import com.toxicbakery.androidthings.mirror.module.weather.model.Weather
import com.toxicbakery.androidthings.mirror.ui.viewholder.ViewHolder
import com.toxicbakery.androidthings.mirror.util.round
import io.reactivex.Observable
import timber.log.Timber
import java.util.*

class CurrentWeatherViewHolder(
        override val rootView: View
) : ViewHolder<CurrentWeather>() {

    private val locationTextView: TextView by bind(R.id.location)
    private val dateTextView: TextView by bind(R.id.date)
    private val weatherSummaryTextView: TextView by bind(R.id.weather_summary)
    private val weatherIconImageView: ImageView by bind(R.id.weather_icon)
    private val temperatureTextView: TextView by bind(R.id.temperature)
    private val temperatureScaleTextView: TextView by bind(R.id.temperature_scale)
    private val resources: Resources = rootView.resources
    private val geoCoder: Geocoder by lazy { Geocoder(rootView.context, Locale.ENGLISH) }

    override fun bind(value: CurrentWeather) {
        val weather: Weather? = value.weather.firstOrNull()
        setLocation(value.coord)
        setDate(value.dt)
        setWeatherSummary(weather?.description ?: "Unknown")
        setWeatherIcon(weather?.icon ?: "")
        setTemperature(value.main.temp)
        setTemperatureScale()
    }

    @SuppressLint("SetTextI18n")
    internal fun setLocation(coord: Coord) {
        Observable.just(coord)
                .doOnNext { coordinates -> Timber.d("Geodecoding coordinates (%s, %s)", coordinates.lat, coordinates.lon) }
                .map { coordinates -> geoCoder.getFromLocation(coordinates.lat, coordinates.lon, 1) }
                .flatMap { addresses -> Observable.fromIterable(addresses) }
                .firstOrError()
                .subscribe(
                        { address -> locationTextView.text = "${address.locality}, ${address.adminArea}" },
                        { e -> Timber.e(e, "Failed to set location") })
    }

    internal fun setDate(date: Long) {
        dateTextView.text = (date * 1000L)
                .let { Date(it) }
                .let { SimpleDateFormat("E h:mm a").format(it) }
    }

    internal fun setWeatherSummary(summary: String) {
        weatherSummaryTextView.text = summary
    }

    internal fun setWeatherIcon(iconId: String) {
        resources.getIdentifier("weather_$iconId", "drawable", BuildConfig.APPLICATION_ID)
                .let { weatherIconImageView.setImageResource(it) }
    }

    internal fun setTemperature(temperature: Double) {
        temperatureTextView.text = "${temperature.round()}"
    }

    internal fun setTemperatureScale() {
        // TODO Use global unit setting
        temperatureScaleTextView.setText(R.string.weather_temperature_unit_imperial)
    }

}