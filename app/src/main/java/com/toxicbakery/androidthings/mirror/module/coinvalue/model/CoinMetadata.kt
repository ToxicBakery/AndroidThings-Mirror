package com.toxicbakery.androidthings.mirror.module.coinvalue.model

import com.google.gson.annotations.SerializedName

data class CoinMetadata(
        @SerializedName("timestamp") val timestamp: Long,
        @SerializedName("error") val error: String?
)