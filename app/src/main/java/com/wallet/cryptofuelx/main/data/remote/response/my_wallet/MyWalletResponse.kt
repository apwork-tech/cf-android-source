package com.wallet.cryptofuelx.main.data.remote.response.my_wallet


import com.google.gson.annotations.SerializedName

data class MyWalletResponse(
    @SerializedName("available_balance")
    var availableBalance: AvailableBalance,
    @SerializedName("message")
    var message: String,
    @SerializedName("success")
    var success: Boolean,
    @SerializedName("wallet_list")
    var walletList: List<Wallet>
)