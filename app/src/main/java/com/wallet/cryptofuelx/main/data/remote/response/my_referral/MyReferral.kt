package com.wallet.cryptofuelx.main.data.remote.response.my_referral


import com.google.gson.annotations.SerializedName

data class MyReferral(
    @SerializedName("message")
    var message: String,
    @SerializedName("my_referral_list")
    var myReferralList: List<MyReferralX>,
    @SerializedName("success")
    var success: Boolean,
    @SerializedName("url")
    var url: String
)