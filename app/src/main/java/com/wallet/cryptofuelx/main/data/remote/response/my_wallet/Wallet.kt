package com.wallet.cryptofuelx.main.data.remote.response.my_wallet


import com.google.gson.annotations.SerializedName

data class Wallet(
    @SerializedName("address")
    var address: List<String>,
    @SerializedName("balance")
    var balance: String,
    @SerializedName("created_at")
    var createdAt: String,
    @SerializedName("encrypt_id")
    var encryptId: String,
    @SerializedName("id")
    var id: Int,
    @SerializedName("is_primary")
    var isPrimary: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("referral_balance")
    var referralBalance: String,
    @SerializedName("status")
    var status: Int,
    @SerializedName("updated_at")
    var updatedAt: String,
    @SerializedName("user_id")
    var userId: Int
)