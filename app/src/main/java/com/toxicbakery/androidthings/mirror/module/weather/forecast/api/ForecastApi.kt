package com.toxicbakery.androidthings.mirror.module.weather.forecast.api

import com.toxicbakery.androidthings.mirror.module.weather.model.ZipCode
import com.toxicbakery.androidthings.mirror.module.weather.forecast.model.Forecast
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastApi {

    @GET("forecast")
    fun forecast(@Query("zip") zipCode: ZipCode): Observable<Response<Forecast>>

}