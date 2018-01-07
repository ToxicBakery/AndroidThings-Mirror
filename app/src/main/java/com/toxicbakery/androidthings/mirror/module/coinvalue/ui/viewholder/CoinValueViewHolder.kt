package com.toxicbakery.androidthings.mirror.module.coinvalue.ui.viewholder

import android.support.v4.content.ContextCompat
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

    private val formatChange: NumberFormat = DecimalFormat("#0.00")
    private val formatPrice: NumberFormat = DecimalFormat("#0.00")

    private val coin1hChangeTextView: TextView by bind(R.id.coin_1h_change)
    private val coinValueTextView: TextView by bind(R.id.coin_value)
    private val coinNameTextView: TextView by bind(R.id.coin_name_and_symbol)

    override fun bind(value: CoinValue) {
        coinNameTextView.resources.apply {
            value.percentChange1H.toDouble()
                    .let(formatChange::format)
                    .let { getString(R.string.coin_value_price_1h_change, it) }
                    .let { coin1hChangeTextView.text = it }

            value.priceUsd.toDouble()
                    .let(formatPrice::format)
                    .let { getString(R.string.coin_value_price_usd, it) }
                    .let { coinValueTextView.text = it }

            getString(R.string.coin_value_name_and_symbol, value.name, value.symbol)
                    .let { coinNameTextView.text = it }
        }

        value.percentChange1H.toDouble()
                .let {
                    if (it >= 0.0) R.color.coin_change_positive
                    else R.color.coin_change_negative
                }
                .let { ContextCompat.getColor(context, it) }
                .let { coin1hChangeTextView.setTextColor(it) }
    }

}