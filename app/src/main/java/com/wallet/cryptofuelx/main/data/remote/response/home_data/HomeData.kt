package com.wallet.cryptofuelx.main.data.remote.response.home_data


import com.google.gson.annotations.SerializedName

data class HomeData(
    @SerializedName("Deposite")
    var deposite: List<String>,
    @SerializedName("label")
    var label: List<String>,
    @SerializedName("message")
    var message: String,
    @SerializedName("sixmonth_diposite_withdrawal")
    var sixmonthDipositeWithdrawal: List<SixmonthDipositeWithdrawal>,
    @SerializedName("success")
    var success: Boolean,
    @SerializedName("Withdraw")
    var withdraw: List<String>
)