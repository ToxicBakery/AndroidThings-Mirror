package com.toxicbakery.androidthings.mirror.module.coinvalue.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import com.toxicbakery.androidthings.mirror.R
import com.toxicbakery.androidthings.mirror.module.coinvalue.kodein.coinValueModule
import com.toxicbakery.androidthings.mirror.module.coinvalue.ui.presenter.CoinValuePresenter
import com.toxicbakery.androidthings.mirror.module.coinvalue.ui.viewholder.CoinValueViewHolder
import com.toxicbakery.androidthings.mirror.ui.view.BaseMvpKodeinFrameLayout

class CoinValueViewLayout @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : BaseMvpKodeinFrameLayout<CoinValueViewHolder, CoinValuePresenter>(context, attrs, defStyleAttr) {

    private val coinName: String
    private val coinId: Int

    init {
        if (isInEditMode) {
            coinName = ""
            coinId = 1
        } else {
            val attributes = context.theme
                    .obtainStyledAttributes(attrs, R.styleable.CoinValueViewLayout, 0, 0)
            coinId = attributes.getInt(R.styleable.CoinValueViewLayout_coinId, 1)
            coinName = attributes.getString(R.styleable.CoinValueViewLayout_coinName) ?: "bitcoin"
            attributes.recycle()
        }
    }

    override fun provideOverridingModule() = Kodein.Module {
        import(coinValueModule)
    }

    override val presenter: CoinValuePresenter by injector.instance()

    override val viewHolder: CoinValueViewHolder = LayoutInflater.from(context)
            .inflate(R.layout.module_coin_value_layout, this, true)
            .let { view -> CoinValueViewHolder(view, coinName, coinId) }

}