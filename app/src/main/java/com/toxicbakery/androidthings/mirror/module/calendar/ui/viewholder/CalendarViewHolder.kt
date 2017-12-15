package com.toxicbakery.androidthings.mirror.module.calendar.ui.viewholder

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.toxicbakery.androidthings.mirror.R
import com.toxicbakery.androidthings.mirror.module.calendar.ui.adapter.CalendarAdapter
import com.toxicbakery.androidthings.mirror.ui.viewholder.ViewHolder
import com.toxicbakery.library.ical.Block

class CalendarViewHolder(
        override val rootView: View,
        private val adapter: CalendarAdapter
) : ViewHolder<List<Block>>() {

    private val recyclerView: RecyclerView by bind(R.id.recycler_view)
    private val noItemsTextView: TextView by bind(R.id.no_items)

    init {
        noItemsTextView.visibility = View.GONE
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
    }

    override fun bind(value: List<Block>) {
        adapter.setIcalBlocks(value)
        noItemsTextView.visibility = if (value.isEmpty()) View.VISIBLE else View.GONE
    }

}