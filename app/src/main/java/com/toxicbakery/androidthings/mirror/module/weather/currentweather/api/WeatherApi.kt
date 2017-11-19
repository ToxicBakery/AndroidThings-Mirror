package com.toxicbakery.androidthings.mirror.module.weather.currentweather.api

import com.toxicbakery.androidthings.mirror.module.weather.currentweather.model.CurrentWeather
import com.toxicbakery.androidthings.mirror.module.weather.model.ZipCode
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    fun currentWeather(@Query("zip") zipCode: ZipCode): Observable<Response<CurrentWeather>>

}