package com.toxicbakery.androidthings.mirror.module.newsfeed.ui.presenter

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.toxicbakery.androidthings.mirror.module.newsfeed.manager.NewsFeedManager
import com.toxicbakery.androidthings.mirror.module.newsfeed.model.Feed
import com.toxicbakery.androidthings.mirror.module.newsfeed.ui.viewholder.NewsFeedViewHolder
import com.toxicbakery.androidthings.mirror.ui.presenter.Presenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class NewsFeedPresenterImpl(
        private val newsFeedManager: NewsFeedManager
) : NewsFeedPresenter {

    private val subscriptions: CompositeDisposable = CompositeDisposable()

    override fun bindViewHolder(viewHolder: NewsFeedViewHolder) {
        subscriptions.addAll(
                newsFeedManager.getNewsFeed()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { updateViewHolder(viewHolder, it) },
                                { Timber.e(it, "Failed to bind view holder") }))
    }

    override fun unbindViewHolder() {
        subscriptions.clear()
    }

    private fun updateViewHolder(viewHolder: NewsFeedViewHolder, feed: Feed) =
            feed.channel
                    .items
                    .take(10)
                    .let(viewHolder::bind)

}

interface NewsFeedPresenter : Presenter<NewsFeedViewHolder>

val newsFeedPresenterModule = Kodein.Module {
    bind<NewsFeedPresenter>() with provider {
        NewsFeedPresenterImpl(
                newsFeedManager = instance()
        )
    }
}