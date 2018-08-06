package com.toxicbakery.androidthings.mirror.module.news.feed.ui.presenter

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.toxicbakery.androidthings.mirror.module.news.feed.manager.NewsFeedManager
import com.toxicbakery.androidthings.mirror.module.news.feed.ui.viewholder.NewsFeedViewHolder
import com.toxicbakery.androidthings.mirror.module.news.model.Item
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
                        .map { feed -> feed.channel.items.take(3) }
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { items -> updateViewHolder(viewHolder, items) },
                                { e -> Timber.e(e, "Failed to bind view holder") }))
    }

    override fun unbindViewHolder() {
        subscriptions.clear()
    }

    private fun updateViewHolder(viewHolder: NewsFeedViewHolder, feed: List<Item>) =
            feed.let(viewHolder::bind)

}

interface NewsFeedPresenter : Presenter<NewsFeedViewHolder>

val newsFeedPresenterModule = Kodein.Module {
    bind<NewsFeedPresenter>() with provider {
        NewsFeedPresenterImpl(
                newsFeedManager = instance()
        )
    }
}