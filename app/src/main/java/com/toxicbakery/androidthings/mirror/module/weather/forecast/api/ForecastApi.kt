package com.toxicbakery.androidthings.mirror.module.weather.forecast.api

import com.toxicbakery.androidthings.mirror.module.weather.forecast.model.Forecast
import com.toxicbakery.androidthings.mirror.module.weather.model.ZipCode
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastApi {

    /*
    FIXME
    cnt should be dynamic however for display purposes we only need ~3 days of weather. Forecast covers 3 hour windows
    and the default return is 40 windows or 5 days.
    */
    @GET("forecast?cnt=24")
    fun forecast(@Query("zip") zipCode: ZipCode): Observable<Response<Forecast>>

}