package com.toxicbakery.androidthings.mirror.module.coinvalue.api

import com.toxicbakery.androidthings.mirror.module.coinvalue.model.CoinValue
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET

interface TickerApi {

    /**
     * Get the current ticker coin value.
     */
    @GET("ticker/?limit=0")
    fun ticker(): Observable<Response<List<CoinValue>>>

}