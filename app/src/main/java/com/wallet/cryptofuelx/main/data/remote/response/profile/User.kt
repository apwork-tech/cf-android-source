package com.wallet.cryptofuelx.main.data.remote.response.profile


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("country")
    var country: String,
    @SerializedName("created_at")
    var createdAt: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("first_name")
    var firstName: String,
    @SerializedName("id")
    var id: Int,
    @SerializedName("is_verified")
    var isVerified: Int,
    @SerializedName("last_name")
    var lastName: String,
    @SerializedName("phone")
    var phone: String,
    @SerializedName("photo")
    var photo: String,
    @SerializedName("status")
    var status: String,
    @SerializedName("type")
    var type: String,
    @SerializedName("updated_at")
    var updatedAt: String
)