package com.toxicbakery.androidthings.mirror.module.coinvalue.model

import com.google.gson.annotations.SerializedName

data class CoinQuote(
        @SerializedName("price") val price: Double,
        @SerializedName("volume_24h") val volume24Hours: Double,
        @SerializedName("market_cap") val marketCap: Double,
        @SerializedName("percent_change_1h") val percentChangeOneHour: Double,
        @SerializedName("percent_change_24h") val percentChangeTwentyFourHour: Double,
        @SerializedName("percent_change_7d") val percentChangeSevenDay: Double
)
