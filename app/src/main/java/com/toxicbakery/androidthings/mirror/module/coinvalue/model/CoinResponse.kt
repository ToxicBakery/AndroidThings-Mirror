package com.toxicbakery.androidthings.mirror.module.coinvalue.model

import com.google.gson.annotations.SerializedName

data class CoinResponse<T>(
        @SerializedName("data") val data: T,
        @SerializedName("metadata") val metaData: CoinMetadata
)