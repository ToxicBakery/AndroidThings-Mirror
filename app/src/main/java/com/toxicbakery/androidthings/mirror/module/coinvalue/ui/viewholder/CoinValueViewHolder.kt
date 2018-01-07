package com.toxicbakery.androidthings.mirror.module.coinvalue.ui.viewholder

import android.view.View
import android.widget.TextView
import com.toxicbakery.androidthings.mirror.R
import com.toxicbakery.androidthings.mirror.module.coinvalue.model.CoinValue
import com.toxicbakery.androidthings.mirror.ui.viewholder.ViewHolder
import java.text.DecimalFormat
import java.text.NumberFormat

class CoinValueViewHolder(
        override val rootView: View,
        internal val coinName: String
) : ViewHolder<CoinValue>() {

    private val formatter: NumberFormat = DecimalFormat("#0.00")

    private val coinValueTextView: TextView by bind(R.id.coin_value)
    private val coinNameTextView: TextView by bind(R.id.coin_name_and_symbol)

    override fun bind(value: CoinValue) {
        val resources = coinNameTextView.resources
        val usd = value.priceUsd.toDouble()
        coinValueTextView.text = resources
                .getString(R.string.coin_value_price_usd, formatter.format(usd))
        coinNameTextView.text = resources
                .getString(R.string.coin_value_name_and_symbol, value.name, value.symbol)
    }

}