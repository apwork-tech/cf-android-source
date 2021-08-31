package com.wallet.cryptofuelx.main.data.remote.response.change_password


import com.google.gson.annotations.SerializedName

data class ChangePassword(
    @SerializedName("message")
    var message: String,
    @SerializedName("status")
    var status: Boolean
)