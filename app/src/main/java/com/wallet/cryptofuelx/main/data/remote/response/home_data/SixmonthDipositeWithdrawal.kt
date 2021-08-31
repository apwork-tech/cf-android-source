package com.wallet.cryptofuelx.main.data.remote.response.home_data


import com.google.gson.annotations.SerializedName

data class SixmonthDipositeWithdrawal(
    @SerializedName("month")
    var month: String,
    @SerializedName("six_deposit")
    var sixDeposit: String,
    @SerializedName("six_withdrawal")
    var sixWithdrawal: String
)