package com.toxicbakery.androidthings.mirror.module.coinvalue.ui.viewholder

import android.content.res.Resources
import android.support.annotation.StringRes
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
        internal val coinName: String,
        internal val coinId: Int
) : ViewHolder<CoinValue>() {

    private val formatChange: NumberFormat = DecimalFormat("#0.00")
    private val formatPriceHundredths: NumberFormat = DecimalFormat("#0.00")
    private val formatPriceThousandths: NumberFormat = DecimalFormat("#0.000")
    private val formatPriceThousands: NumberFormat = DecimalFormat("#0")

    private val coin1hChangeTextView: TextView by bind(R.id.coin_1h_change)
    private val coinValueTextView: TextView by bind(R.id.coin_value)
    private val coinNameTextView: TextView by bind(R.id.coin_name_and_symbol)

    private val resources: Resources
        get() = rootView.resources

    private fun getString(@StringRes id: Int, vararg formatArgs: Any) =
            resources.getString(id, *formatArgs)

    override fun bind(value: CoinValue) {
        (value.quotes["USD"]?.percentChangeOneHour ?: 0.0)
                .let(formatChange::format)
                .let { getString(R.string.coin_value_price_1h_change, it) }
                .let { coin1hChangeTextView.text = it }

        (value.quotes["USD"]?.price ?: 0.0)
                .let { price ->
                    when {
                        price > 1000 -> formatPriceThousands.format(price)
                        price > 1 -> formatPriceHundredths.format(price)
                        else -> formatPriceThousandths.format(price)
                    }
                }
                .let { getString(R.string.coin_value_price_usd, it) }
                .let { coinValueTextView.text = it }

        getString(R.string.coin_value_name_and_symbol, value.name, value.symbol)
                .let { coinNameTextView.text = it }

        (value.quotes["USD"]?.percentChangeOneHour ?: 0.0)
                .let { percentChange ->
                    if (percentChange.isPositive()) R.color.coin_change_positive
                    else R.color.coin_change_negative
                }
                .let { ContextCompat.getColor(context, it) }
                .let { coin1hChangeTextView.setTextColor(it) }
    }

    private fun Double.isPositive(): Boolean = this >= 0
}