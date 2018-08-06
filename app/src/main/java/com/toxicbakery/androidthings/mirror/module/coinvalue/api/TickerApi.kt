package com.toxicbakery.androidthings.mirror.module.coinvalue.api

import com.toxicbakery.androidthings.mirror.module.coinvalue.model.CoinResponse
import com.toxicbakery.androidthings.mirror.module.coinvalue.model.CoinValue
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TickerApi {

    /**
     * Get the current ticker coin value.
     */
    @GET("ticker/{id}/?limit=0")
    fun ticker(@Path("id") id: Int): Observable<Response<CoinResponse<CoinValue>>>

}