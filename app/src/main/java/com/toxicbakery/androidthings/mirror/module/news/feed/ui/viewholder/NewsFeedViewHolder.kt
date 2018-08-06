package com.toxicbakery.androidthings.mirror.module.news.feed.ui.viewholder

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.toxicbakery.androidthings.mirror.R
import com.toxicbakery.androidthings.mirror.module.news.feed.ui.adapter.NewsFeedAdapter
import com.toxicbakery.androidthings.mirror.module.news.model.Item
import com.toxicbakery.androidthings.mirror.ui.viewholder.ViewHolder

class NewsFeedViewHolder(
        override val rootView: View,
        private val adapter: NewsFeedAdapter
) : ViewHolder<List<Item>>() {

    private val layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.VERTICAL, false)

    private val recyclerView: RecyclerView by bind(R.id.recycler_view)
    private val noItemsTextView: TextView by bind(R.id.no_items)

    init {
        noItemsTextView.visibility = View.GONE
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    override fun bind(value: List<Item>) {
        adapter.changeFeedItems(value)
        noItemsTextView.visibility = if (value.isEmpty()) View.VISIBLE else View.GONE
    }

}