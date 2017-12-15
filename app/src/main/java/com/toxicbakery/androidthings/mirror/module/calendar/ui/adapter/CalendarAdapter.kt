package com.toxicbakery.androidthings.mirror.module.calendar.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.toxicbakery.androidthings.mirror.R
import com.toxicbakery.library.ical.Block

class CalendarAdapter : RecyclerView.Adapter<CalendarRowViewHolder>() {

    private var blocks: List<Block> = emptyList()

    override fun onBindViewHolder(holder: CalendarRowViewHolder, position: Int) =
            holder.bind(getItem(position))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarRowViewHolder =
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.calendar_row, parent, false)
                    .let { CalendarRowViewHolder(it) }

    override fun getItemCount(): Int = blocks.size

    fun setIcalBlocks(blocks: List<Block>) {
        // FIXME Switch this to use list diff instead of dumb update
        notifyItemRangeRemoved(0, itemCount)
        this.blocks = blocks
        notifyItemRangeInserted(0, itemCount)
    }

    private fun getItem(position: Int): Block = blocks[position]

}