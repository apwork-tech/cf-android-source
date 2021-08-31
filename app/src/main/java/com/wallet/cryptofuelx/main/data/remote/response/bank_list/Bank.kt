package com.wallet.cryptofuelx.main.data.remote.response.bank_list


import com.google.gson.annotations.SerializedName

data class Bank(
    @SerializedName("account_holder_address")
    var accountHolderAddress: String,
    @SerializedName("account_holder_name")
    var accountHolderName: String,
    @SerializedName("bank_address")
    var bankAddress: String,
    @SerializedName("bank_name")
    var bankName: String,
    @SerializedName("country")
    var country: String,
    @SerializedName("created_at")
    var createdAt: String,
    @SerializedName("iban")
    var iban: String,
    @SerializedName("id")
    var id: Int,
    @SerializedName("note")
    var note: String,
    @SerializedName("status")
    var status: Int,
    @SerializedName("swift_code")
    var swiftCode: String,
    @SerializedName("updated_at")
    var updatedAt: String
)