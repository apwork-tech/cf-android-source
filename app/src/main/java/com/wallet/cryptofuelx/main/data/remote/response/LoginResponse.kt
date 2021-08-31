package com.wallet.cryptofuelx.main.data.remote.response

import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName
import com.wallet.cryptofuelx.utils.helper.Constants

/**
 * This is model class for response of login page
 * @author Mohd. Asfaq-E-Azam Rifat
 */
data class LoginResponse(
        @SerializedName(Constants.JsonKeys.SUCCESS) var isSuccessful: Boolean,
        @SerializedName(Constants.JsonKeys.DATA) var data: JsonElement,
        @SerializedName(Constants.JsonKeys.IS_VERIFIED) var isVerified:Boolean,
        @SerializedName(Constants.JsonKeys.MESSAGE) var message: String)