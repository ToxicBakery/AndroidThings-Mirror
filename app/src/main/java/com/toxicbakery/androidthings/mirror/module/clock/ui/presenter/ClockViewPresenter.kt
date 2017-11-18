package com.toxicbakery.androidthings.mirror.module.clock.ui.presenter

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.toxicbakery.androidthings.mirror.module.clock.manager.ClockManager
import com.toxicbakery.androidthings.mirror.module.clock.ui.viewholder.ClockViewHolder
import com.toxicbakery.androidthings.mirror.ui.presenter.Presenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class ClockViewPresenterImpl(
        private val clockManager: ClockManager
) : ClockViewPresenter {

    private val subscriptions: CompositeDisposable = CompositeDisposable()

    override fun bindViewHolder(viewHolder: ClockViewHolder) {
        subscriptions.addAll(
                clockManager.getDateTime()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ viewHolder.bind(it) },
                                { Timber.e(it, "Failed to bind view holder") }))
    }

    override fun unbindViewHolder() {
        subscriptions.clear()
    }

}

interface ClockViewPresenter : Presenter<ClockViewHolder>

val clockViewPresenterModule = Kodein.Module {
    bind<ClockViewPresenter>() with provider { ClockViewPresenterImpl(instance()) }
}