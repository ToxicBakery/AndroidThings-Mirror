package com.toxicbakery.androidthings.mirror.module.coinvalue.manager

import com.github.salomonbrys.kodein.*
import com.toxicbakery.androidthings.mirror.api.responseMapper
import com.toxicbakery.androidthings.mirror.module.coinvalue.api.TickerApi
import com.toxicbakery.androidthings.mirror.module.coinvalue.model.CoinValue
import io.reactivex.Observable
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class CoinValueManagerImpl(
        coinValueUpdateManager: CoinValueUpdateManager
) : CoinValueManager {

    private val update =
            coinValueUpdateManager.updateCoinValues()
                    .share()

    override fun getCoinValue(coinName: String): Observable<CoinValue> =
            update.flatMap { Observable.fromIterable(it) }
                    .filter { it.id == coinName }

}

class CoinValueUpdateManagerImpl(
        private val tickerApi: TickerApi
) : CoinValueUpdateManager {

    override fun updateCoinValues(): Observable<List<CoinValue>> =
            Observable.interval(0, 5, TimeUnit.MINUTES)
                    .flatMap { tickerApi.ticker() }
                    .map { responseMapper(it) }

}

interface CoinValueManager {

    fun getCoinValue(coinName: String): Observable<CoinValue>

}

interface CoinValueUpdateManager {

    fun updateCoinValues(): Observable<List<CoinValue>>

}

val coinValueManagerModule = Kodein.Module {
    bind<TickerApi>() with provider { instance<Retrofit>().create(TickerApi::class.java) }
    bind<CoinValueUpdateManager>() with provider {
        CoinValueUpdateManagerImpl(
                tickerApi = instance()
        )
    }
    bind<CoinValueManager>() with singleton {
        CoinValueManagerImpl(
                coinValueUpdateManager = instance()
        )
    }
}