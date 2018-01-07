package com.toxicbakery.androidthings.mirror.module.coinvalue.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import com.toxicbakery.androidthings.mirror.R
import com.toxicbakery.androidthings.mirror.module.coinvalue.kodein.coinValueKodein
import com.toxicbakery.androidthings.mirror.module.coinvalue.ui.presenter.CoinValuePresenter
import com.toxicbakery.androidthings.mirror.module.coinvalue.ui.viewholder.CoinValueViewHolder
import com.toxicbakery.androidthings.mirror.ui.view.BaseMvpKodeinFrameLayout

class CoinValueViewLayout @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : BaseMvpKodeinFrameLayout<CoinValueViewHolder, CoinValuePresenter>(context, attrs, defStyleAttr) {

    private val coinName: String

    init {
        if (isInEditMode) {
            coinName = ""
        } else {
            val attributes = context.theme
                    .obtainStyledAttributes(attrs, R.styleable.CoinValueViewLayout, 0, 0)
            coinName = attributes.getString(R.styleable.CoinValueViewLayout_coinName)
            attributes.recycle()
        }
    }

    override fun provideOverridingKodein(): Kodein = Kodein {
        extend(coinValueKodein)
    }

    override val presenter: CoinValuePresenter by injector.instance()

    override val viewHolder: CoinValueViewHolder = LayoutInflater.from(context)
            .inflate(R.layout.module_coin_value_layout, this, true)
            .let { CoinValueViewHolder(it, coinName) }

}