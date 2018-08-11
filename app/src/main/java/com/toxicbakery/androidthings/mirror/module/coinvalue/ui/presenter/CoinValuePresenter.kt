package com.toxicbakery.androidthings.mirror.module.coinvalue.ui.presenter

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.toxicbakery.androidthings.mirror.module.coinvalue.manager.CoinValueManager
import com.toxicbakery.androidthings.mirror.module.coinvalue.ui.viewholder.CoinValueViewHolder
import com.toxicbakery.androidthings.mirror.ui.presenter.Presenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class CoinValuePresenterImpl(
        private val coinValueManager: CoinValueManager
) : CoinValuePresenter {

    private val subscriptions: CompositeDisposable = CompositeDisposable()

    override fun bindViewHolder(viewHolder: CoinValueViewHolder) {
        subscriptions.addAll(
                coinValueManager.getCoinValue(viewHolder.coinId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(viewHolder::bind)
                        { e ->
                            Timber.e(e, "Failed to bind view holder for coin %s", viewHolder.coinName)
                        })
    }

    override fun unbindViewHolder() {
        subscriptions.clear()
    }

}

interface CoinValuePresenter : Presenter<CoinValueViewHolder>

val coinValuePresenterModule = Kodein.Module {
    bind<CoinValuePresenter>() with provider {
        CoinValuePresenterImpl(
                coinValueManager = instance()
        )
    }
}