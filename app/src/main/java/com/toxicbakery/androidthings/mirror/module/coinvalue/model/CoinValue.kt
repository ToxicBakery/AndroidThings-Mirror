package com.toxicbakery.androidthings.mirror.module.coinvalue.model

import com.google.gson.annotations.SerializedName

data class CoinValue(
        @SerializedName("id") val id: String,
        @SerializedName("name") val name: String,
        @SerializedName("symbol") val symbol: String,
        @SerializedName("rank") val rank: String,
        @SerializedName("price_usd") val priceUsd: String,
        @SerializedName("price_btc") val priceBtc: String,
        @SerializedName("24_volume_usd") val volumeUsd: String,
        @SerializedName("market_cap_usd") val marketCapUsd: String,
        @SerializedName("available_supply") val availableSupply: String,
        @SerializedName("total_supply") val totalSupply: String,
        @SerializedName("max_supply") val maxSupply: String,
        @SerializedName("percent_change_1h") val percentChange1H: String,
        @SerializedName("percent_change_21h") val percentChange24H: String,
        @SerializedName("percent_change_7d") val percentChange7D: String,
        @SerializedName("last_updated") val lastUpdated: String
)