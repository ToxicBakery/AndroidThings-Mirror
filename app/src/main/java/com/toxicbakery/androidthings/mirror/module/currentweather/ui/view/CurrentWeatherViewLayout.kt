package com.toxicbakery.androidthings.mirror.module.currentweather.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.github.salomonbrys.kodein.instance
import com.toxicbakery.androidthings.mirror.R
import com.toxicbakery.androidthings.mirror.module.currentweather.ui.presenter.CurrentWeatherPresenter
import com.toxicbakery.androidthings.mirror.module.currentweather.ui.viewholder.CurrentWeatherViewHolder
import com.toxicbakery.androidthings.mirror.ui.view.BaseMvpKodeinFrameLayout

class CurrentWeatherViewLayout @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : BaseMvpKodeinFrameLayout<CurrentWeatherViewHolder, CurrentWeatherPresenter>(context, attrs, defStyleAttr) {

    override val presenter: CurrentWeatherPresenter by injector.instance()

    override val viewHolder: CurrentWeatherViewHolder = LayoutInflater.from(context)
            .inflate(R.layout.module_current_weather_layout, this, true)
            .let { CurrentWeatherViewHolder(it) }

}