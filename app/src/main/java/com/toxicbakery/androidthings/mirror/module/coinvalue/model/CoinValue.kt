package com.toxicbakery.androidthings.mirror.module.coinvalue.model

import com.google.gson.annotations.SerializedName

data class CoinValue(
        @SerializedName("id") val id: String,
        @SerializedName("name") val name: String,
        @SerializedName("symbol") val symbol: String,
        @SerializedName("rank") val rank: String,
        @SerializedName("circulating_supply") val circulatingSupply: Double,
        @SerializedName("total_supply") val totalSupply: String,
        @SerializedName("max_supply") val maxSupply: String,
        @SerializedName("last_updated") val lastUpdated: String,
        @SerializedName("quotes") val quotes: Map<String, CoinQuote>
)