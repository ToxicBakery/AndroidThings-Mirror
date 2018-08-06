package com.toxicbakery.androidthings.mirror.module.news.feed.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.toxicbakery.androidthings.mirror.R
import com.toxicbakery.androidthings.mirror.module.news.model.Item
import com.toxicbakery.androidthings.mirror.util.autoNotify
import kotlin.properties.Delegates

class NewsFeedAdapter : RecyclerView.Adapter<NewsFeedRowViewHolder>() {

    private var items: List<Item> by Delegates.observable(emptyList()) { _, old, new ->
        autoNotify(old, new) { left, right -> left == right }
    }

    override fun onBindViewHolder(holder: NewsFeedRowViewHolder, position: Int) =
            getItem(position)
                    .let { holder.bind(it) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsFeedRowViewHolder =
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.news_feed_row, parent, false)
                    .let(::NewsFeedRowViewHolder)

    override fun getItemCount(): Int = items.size

    fun changeFeedItems(items: List<Item>) {
        this.items = items
    }

    private fun getItem(position: Int): Item = items[position]

}