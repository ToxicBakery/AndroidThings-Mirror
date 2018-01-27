package com.toxicbakery.androidthings.mirror.module.coinvalue.manager

import com.github.salomonbrys.kodein.*
import com.toxicbakery.androidthings.mirror.api.responseMapper
import com.toxicbakery.androidthings.mirror.module.coinvalue.api.TickerApi
import com.toxicbakery.androidthings.mirror.module.coinvalue.model.CoinValue
import io.reactivex.Observable
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class CoinValueManagerImpl(
        private val tickerApi: TickerApi
) : CoinValueManager {

    private val update =
            Observable.interval(0, 5, TimeUnit.MINUTES)
                    .flatMap { tickerApi.ticker().onErrorResumeNext(Observable.empty()) }
                    .map { responseMapper(it) }
                    .share()

    override fun getCoinValue(coinName: String): Observable<CoinValue> =
            update.flatMap { Observable.fromIterable(it) }
                    .filter { it.id == coinName }

}

interface CoinValueManager {

    fun getCoinValue(coinName: String): Observable<CoinValue>

}

val coinValueManagerModule = Kodein.Module {
    bind<TickerApi>() with provider { instance<Retrofit>().create(TickerApi::class.java) }
    bind<CoinValueManager>() with singleton {
        CoinValueManagerImpl(
                tickerApi = instance()
        )
    }
}