package com.wallet.cryptofuelx.main.data.remote.response.wallet_transaction


import com.google.gson.annotations.SerializedName

data class AvailableBalance(
    @SerializedName("available_coin")
    var availableCoin: String,
    @SerializedName("available_usd")
    var availableUsd: String,
    @SerializedName("coin_name")
    var coinName: String
)