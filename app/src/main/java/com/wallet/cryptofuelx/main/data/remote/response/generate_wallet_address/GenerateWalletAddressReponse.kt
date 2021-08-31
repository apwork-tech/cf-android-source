package com.wallet.cryptofuelx.main.data.remote.response.generate_wallet_address


import com.google.gson.annotations.SerializedName

data class GenerateWalletAddressReponse(
    @SerializedName("address_list")
    var addressList: List<String>,
    @SerializedName("message")
    var message: String,
    @SerializedName("success")
    var success: Boolean
)