package com.wallet.cryptofuelx.main.data.remote.response.buy_coin


import com.google.gson.annotations.SerializedName

data class BuyCoin(
    @SerializedName("available_balance")
    var availableBalance: AvailableBalance,
    @SerializedName("message")
    var message: String,
    @SerializedName("success")
    var success: Boolean
)