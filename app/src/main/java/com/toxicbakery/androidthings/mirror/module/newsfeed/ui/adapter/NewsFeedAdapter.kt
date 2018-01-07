package com.toxicbakery.androidthings.mirror.module.newsfeed.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.toxicbakery.androidthings.mirror.R
import com.toxicbakery.androidthings.mirror.module.newsfeed.model.Item

class NewsFeedAdapter : RecyclerView.Adapter<NewsFeedRowViewHolder>() {

    private var items: List<Item> = emptyList()

    override fun onBindViewHolder(holder: NewsFeedRowViewHolder, position: Int) =
            getItem(position)
                    .let { holder.bind(it) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsFeedRowViewHolder =
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.news_feed_row, parent, false)
                    .let(::NewsFeedRowViewHolder)

    override fun getItemCount(): Int = items.size

    fun setFeedItems(items: List<Item>) {
        // FIXME Switch this to use list diff instead of dumb update
        notifyItemRangeRemoved(0, itemCount)
        this.items = items
        notifyItemRangeInserted(0, itemCount)
    }

    private fun getItem(position: Int): Item = items[position]

}