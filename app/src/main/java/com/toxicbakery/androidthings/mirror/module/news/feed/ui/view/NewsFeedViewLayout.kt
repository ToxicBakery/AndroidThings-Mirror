package com.toxicbakery.androidthings.mirror.module.news.feed.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import com.toxicbakery.androidthings.mirror.R
import com.toxicbakery.androidthings.mirror.module.news.feed.kodein.newsFeedModule
import com.toxicbakery.androidthings.mirror.module.news.feed.ui.adapter.NewsFeedAdapter
import com.toxicbakery.androidthings.mirror.module.news.feed.ui.presenter.NewsFeedPresenter
import com.toxicbakery.androidthings.mirror.module.news.feed.ui.viewholder.NewsFeedViewHolder
import com.toxicbakery.androidthings.mirror.ui.view.BaseMvpKodeinFrameLayout

class NewsFeedViewLayout @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : BaseMvpKodeinFrameLayout<NewsFeedViewHolder, NewsFeedPresenter>(context, attrs, defStyleAttr) {

    private val adapter = NewsFeedAdapter()

    override fun provideOverridingModule() = Kodein.Module {
        import(newsFeedModule)
    }

    override val presenter: NewsFeedPresenter by injector.instance()

    override val viewHolder: NewsFeedViewHolder = LayoutInflater.from(context)
            .inflate(R.layout.module_news_feed_layout, this, true)
            .let { NewsFeedViewHolder(it, adapter) }

}