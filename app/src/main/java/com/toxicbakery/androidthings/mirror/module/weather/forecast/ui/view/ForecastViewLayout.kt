package com.toxicbakery.androidthings.mirror.module.weather.forecast.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import com.toxicbakery.androidthings.mirror.R
import com.toxicbakery.androidthings.mirror.module.weather.forecast.kodein.forecastKodein
import com.toxicbakery.androidthings.mirror.module.weather.forecast.ui.presenter.ForecastPresenter
import com.toxicbakery.androidthings.mirror.module.weather.forecast.ui.viewholder.ForecastViewHolder
import com.toxicbakery.androidthings.mirror.module.weather.kodein.weatherKodein
import com.toxicbakery.androidthings.mirror.ui.view.BaseMvpKodeinFrameLayout

class ForecastViewLayout @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : BaseMvpKodeinFrameLayout<ForecastViewHolder, ForecastPresenter>(context, attrs, defStyleAttr) {

    override fun provideOverridingKodein(): Kodein = Kodein {
        extend(weatherKodein)
        extend(forecastKodein)
    }

    override val presenter: ForecastPresenter by injector.instance()

    override val viewHolder: ForecastViewHolder = LayoutInflater.from(context)
            .inflate(R.layout.module_forecast_layout, this, true)
            .let { ForecastViewHolder(it) }

}