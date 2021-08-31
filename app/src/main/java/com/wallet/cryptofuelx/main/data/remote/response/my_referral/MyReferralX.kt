package com.wallet.cryptofuelx.main.data.remote.response.my_referral


import com.google.gson.annotations.SerializedName

data class MyReferralX(
    @SerializedName("email")
    var email: String,
    @SerializedName("full_name")
    var fullName: String,
    @SerializedName("id")
    var id: Int,
    @SerializedName("joining_date")
    var joiningDate: String,
    @SerializedName("level")
    var level: String
)