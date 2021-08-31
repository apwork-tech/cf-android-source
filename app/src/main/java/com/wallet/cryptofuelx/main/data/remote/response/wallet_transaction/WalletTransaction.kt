package com.wallet.cryptofuelx.main.data.remote.response.wallet_transaction


import com.google.gson.annotations.SerializedName

data class WalletTransaction(
    @SerializedName("available_balance")
    var availableBalance: AvailableBalance,
    @SerializedName("message")
    var message: String,
    @SerializedName("success")
    var success: Boolean,
    @SerializedName("transaction_list")
    var transactionList: List<Transaction>,
    @SerializedName("user_id")
    var userId: Int
)