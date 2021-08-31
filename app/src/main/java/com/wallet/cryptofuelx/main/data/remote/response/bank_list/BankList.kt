package com.wallet.cryptofuelx.main.data.remote.response.bank_list


import com.google.gson.annotations.SerializedName

data class BankList(
    @SerializedName("available_balance")
    var availableBalance: AvailableBalance,
    @SerializedName("bank_list")
    var bankList: List<Bank>,
    @SerializedName("message")
    var message: String,
    @SerializedName("success")
    var success: Boolean
)