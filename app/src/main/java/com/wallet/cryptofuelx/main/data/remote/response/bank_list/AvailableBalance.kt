package com.wallet.cryptofuelx.main.data.remote.response.bank_list


import com.google.gson.annotations.SerializedName

data class AvailableBalance(
    @SerializedName("available_coin")
    var availableCoin: String,
    @SerializedName("available_usd")
    var availableUsd: String,
    @SerializedName("coin_name")
    var coinName: String
)