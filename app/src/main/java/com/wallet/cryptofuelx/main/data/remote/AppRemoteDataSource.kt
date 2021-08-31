package com.wallet.cryptofuelx.main.data.remote

import android.content.Context
import com.wallet.cryptofuelx.R
import com.wallet.cryptofuelx.main.data.remote.response.*
import com.wallet.cryptofuelx.main.data.remote.response.bank_list.BankList
import com.wallet.cryptofuelx.main.data.remote.response.buy_coin.BuyCoin
import com.wallet.cryptofuelx.main.data.remote.response.change_password.ChangePassword
import com.wallet.cryptofuelx.main.data.remote.response.generate_wallet_address.GenerateWalletAddressReponse
import com.wallet.cryptofuelx.main.data.remote.response.home_data.HomeData
import com.wallet.cryptofuelx.main.data.remote.response.my_referral.MyReferral
import com.wallet.cryptofuelx.main.data.remote.response.my_wallet.MyWalletResponse
import com.wallet.cryptofuelx.main.data.remote.response.profile.ProfileResponse
import com.wallet.cryptofuelx.main.data.remote.response.wallet_transaction.WalletTransaction
import com.wallet.cryptofuelx.main.data.remote.service.cryptwallet.ApiService
import com.wallet.cryptofuelx.utils.helper.Constants
import com.wallet.cryptofuelx.utils.helper.DataUtils
import com.wallet.cryptofuelx.utils.helper.SharedPrefUtils
import io.reactivex.Flowable
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File

/**
 * This is the remote data source class of the project. This class contains all the basic methods
 * needed for remote purposes.
 * @author Mohd. Asfaq-E-Azam Rifat
 */
class AppRemoteDataSource(context: Context) {

    /**
     * This method registers an user
     *
     * @param name name of the user
     * @param email email of the user
     * @param mobilePhone mobile number of the user
     * @param password password of the user
     * @param token FCM token of device in order to send push notifications in future
     * @return base response consists of success status, data and message
     * */
    fun register(name: String, lastName: String,
                 email: String, mobilePhone: String, password: String, confirmPassword: String):
            Flowable<retrofit2.Response<BaseResponse>> {
        return ApiService.on().register(name, lastName, email, mobilePhone, password, confirmPassword
        ).onBackpressureLatest()
    }

    /**
     * This method logs in an user
     *
     * @param email email of the user
     * @param password password of the user
     * @param token FCM token of device in order to send push notifications in future
     * @return response consists of success status, data and message
     * */
    fun login(email: String, password: String):
            Flowable<retrofit2.Response<LoginResponse>> {
        return ApiService.on().login(email, password).onBackpressureLatest()
    }


    /**
     * @param email email of the user
     * @param access_code this code sent to user email
     * @return response of the web service
     */
    fun verifyEmail(
            email: String,
            access_code: String
    ): Flowable<retrofit2.Response<BaseResponse>> {
        return ApiService.on().verifyEmail(email, access_code).onBackpressureLatest()
    }


    /**
     * This method resets the password of the user account
     *
     * @param email email of the user
     * @return response consists of success status, data and message
     * */
    fun resetPassword(email: String): Flowable<retrofit2.Response<BaseResponse>> {
        return ApiService.on().resetPassword(email).onBackpressureLatest()
    }


    /**
     * This method logs out the user
     * @return response consists of success status
     * */
    fun logOut(): Flowable<retrofit2.Response<BaseResponse>> {
        return ApiService.on().logOut((Constants.Common.ROLE +
                SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TOKEN)),
                DataUtils.getInteger(R.integer.device_type),
                SharedPrefUtils.readString(Constants.PreferenceKeys.FCM_TOKEN)).onBackpressureLatest()
    }

    /**
     * This method gets profile of the user
     * @return response consists of user profile
     * */
    fun getUserProfile(): Flowable<retrofit2.Response<ProfileResponse>> {
        return ApiService.on().getUserProfile(Constants.Common.ROLE +
                SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TOKEN)).onBackpressureLatest()
    }

    /*
   * This method get the wallet list of user
   *
   * */
    fun getWalletList():
            Flowable<Response<MyWalletResponse>> {
        return ApiService.on().getWalletList(
                Constants.Common.ROLE + SharedPrefUtils.readString(
                        Constants.PreferenceKeys.ACCESS_TOKEN
                )
        ).onBackpressureLatest()
    }

    /*
 * This method create wallet for a user
 * */
    fun createWallet(walletName: String): Flowable<Response<BaseResponse>> {
        return ApiService.on().createWallet(
                Constants.Common.ROLE + SharedPrefUtils.readString(
                        Constants.PreferenceKeys.ACCESS_TOKEN
                ),
                walletName
        ).onBackpressureLatest()
    }

    /*
    * This function update user info
    * */
    fun updateProfile(
            firstName: String,
            lastName: String,
            country: String,
            mobile: String,
            image: File?
    ): Flowable<Response<ProfileResponse>> {
        val nameBody = firstName.toRequestBody(MultipartBody.FORM)
        val lastNameBody = lastName.toRequestBody(MultipartBody.FORM)
        val countryBody = country.toRequestBody(MultipartBody.FORM)
        val mobileBody = mobile.toRequestBody(MultipartBody.FORM)
        val imageFileBody = image?.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        var imagePart: MultipartBody.Part?
        imagePart = if (imageFileBody != null) {
            MultipartBody.Part.createFormData("photo", image!!.name, imageFileBody!!)
        } else {
            null
        }

        return ApiService.on().updateProfile(
                Constants.Common.ROLE + SharedPrefUtils.readString(
                        Constants.PreferenceKeys.ACCESS_TOKEN
                ),
                nameBody,
                lastNameBody,
                countryBody,
                mobileBody,
                imagePart
        )
    }

    /*
    * This function buy coin
    * */
    fun buyCoin(paymentType: Int,
                bankId: Int?,
                coin: Double,
                paymentMethodNonce: String?,
                sleep: File?
    ): Flowable<Response<BuyCoin>> {
        val paymentTypeBody = paymentType.toString().toRequestBody(MultipartBody.FORM)
        val bankIdBody = bankId?.toString()?.toRequestBody(MultipartBody.FORM)
        val coinBody = coin.toString().toRequestBody(MultipartBody.FORM)
        val paymentMethodNonceBody = paymentMethodNonce?.toRequestBody(MultipartBody.FORM)
        val sleepBody = sleep?.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        var sleepPart: MultipartBody.Part? = if (sleep != null) {
            MultipartBody.Part.createFormData("sleep", sleep!!.name, sleepBody!!)
        } else {
            null
        }
        return ApiService.on().buyCoin(
                Constants.Common.ROLE + SharedPrefUtils.readString(
                        Constants.PreferenceKeys.ACCESS_TOKEN
                ),
                paymentTypeBody,
                coin,
                paymentMethodNonceBody,
                bankIdBody,
                sleepPart
        ).onBackpressureLatest()
    }

    /*
    * This function change current password
    * */
    fun changePassword(currentPass: String,
                       newPass: String,
                       repeatPassword: String
    ): Flowable<Response<ChangePassword>> {
        return ApiService.on().changePassword(
                Constants.Common.ROLE + SharedPrefUtils.readString(
                        Constants.PreferenceKeys.ACCESS_TOKEN
                ),
                currentPass,
                newPass,
                repeatPassword
        ).onBackpressureLatest()
    }

    fun getHomeData(time: String): Flowable<Response<HomeData>> {
        return ApiService.on().getHomeData(
                Constants.Common.ROLE + SharedPrefUtils.readString(
                        Constants.PreferenceKeys.ACCESS_TOKEN
                ),
                time
        ).onBackpressureLatest()
    }

    /*
    * This method reset password
    * */
    fun resetPassword(email: String,
                      access_code: String,
                      password: String,
                      confirmPassword: String
    ): Flowable<Response<BaseResponse>> {
        return ApiService.on().resetPassword(
                email,
                access_code, password, confirmPassword
        ).onBackpressureLatest()
    }



    /*
   * This method generate new address for a wallet
   * */
    fun generateWalletAddress(walletId: Int):
            Flowable<Response<GenerateWalletAddressReponse>> {
        return ApiService.on().generateWalletAddress(
                Constants.Common.ROLE + SharedPrefUtils.readString(
                        Constants.PreferenceKeys.ACCESS_TOKEN),
                walletId
        ).onBackpressureLatest()
    }

    /*
    * This function withdraw balance from a wallet
    * */

    fun withdrawBalance(walletId: Int,
                        walletAddress: String,
                        amount: Double
    ): Flowable<Response<BaseResponse>> {
        return ApiService.on().withdrawBalance(
                Constants.Common.ROLE + SharedPrefUtils.readString(
                        Constants.PreferenceKeys.ACCESS_TOKEN),
                walletId,
                walletAddress,
                amount
        ).onBackpressureLatest()
    }

    /*
    * This method get my referral url and list
    * */
    fun myReferral()
            : Flowable<Response<MyReferral>> {
        return ApiService.on().myReferral(
                Constants.Common.ROLE + SharedPrefUtils.readString(
                        Constants.PreferenceKeys.ACCESS_TOKEN)
        )
    }

    /*
* This method get a single wallet transaction history
* */
    fun getWalletHistory(encriptedId: String): Flowable<Response<WalletTransaction>> {
        return ApiService.on().getWalletHistory(Constants.Common.ROLE + SharedPrefUtils.readString(
                Constants.PreferenceKeys.ACCESS_TOKEN),
                DataUtils.getString(R.string.api_base_url) + Constants.API.WALLET_HISTORY + encriptedId
        )
    }

    /*
  * This method get bank list from server
  * */
    fun getBankList(): Flowable<Response<BankList>> {
        return ApiService.on().getBankList(Constants.Common.ROLE + SharedPrefUtils.readString(
                Constants.PreferenceKeys.ACCESS_TOKEN))
    }

}