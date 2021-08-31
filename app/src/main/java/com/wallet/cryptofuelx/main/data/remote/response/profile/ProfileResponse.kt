package com.wallet.cryptofuelx.main.data.remote.response.profile


import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @SerializedName("message")
    var message: String,
    @SerializedName("success")
    var success: Boolean,
    @SerializedName("user")
    var user: User
)