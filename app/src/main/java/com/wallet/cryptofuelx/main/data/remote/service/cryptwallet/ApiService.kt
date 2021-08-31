package com.wallet.cryptofuelx.main.data.remote.service.cryptwallet

import com.google.gson.GsonBuilder
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
import com.wallet.cryptofuelx.main.data.remote.service.ConnectivityInterceptor
import com.wallet.cryptofuelx.utils.helper.Constants
import com.wallet.cryptofuelx.utils.helper.DataUtils
import io.reactivex.Flowable
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

/**
 * This is the API service interface of the project. This interface contains all the basic methods
 * needed for remote purposes.
 * @author Mohd. Asfaq-E-Azam Rifat
 */
interface ApiService {
    /**
     * This method registers an user
     *
     * @param name name of the user
     * @param email email of the user
     * @param mobilePhone mobile number of the user
     * @param password password of the user
     * @param deviceType type of the device
     * @param token FCM token of device in order to send push notifications in future
     * @return base response consists of success status, data and message
     * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @POST(Constants.API.REGISTER)
    fun register(@Query(Constants.API.Body.Field.FIRST_NAME) name: String,
                 @Query(Constants.API.Body.Field.LAST_NAME) lastName: String,
                 @Query(Constants.API.Body.Field.EMAIL) email: String,
                 @Query(Constants.API.Body.Field.MOBILE) mobilePhone: String,
                 @Query(Constants.API.Body.Field.PASSWORD) password: String,
                 @Query(Constants.API.Body.Field.CONFRIM_PASSWORD) confirmPassword: String)
            : Flowable<retrofit2.Response<BaseResponse>>

    /**
     * This method logs in an user
     *
     * @param email email of the user
     * @param password password of the user
     * @param deviceType type of the device
     * @param token FCM token of device in order to send push notifications in future
     * @return response consists of success status, data and message
     * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @POST(Constants.API.LOGIN)
    fun login(@Query(Constants.API.Body.Field.EMAIL) email: String,
              @Query(Constants.API.Body.Field.PASSWORD) password: String)
            : Flowable<retrofit2.Response<LoginResponse>>


    /**
     * @param email email of the user
     * @param access_code this code sent to user email
     * @return response of the web service
     */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @POST(Constants.API.VERIFY_EMAIL)
    fun verifyEmail(
            @Query(Constants.API.Body.Field.EMAIL) email: String,
            @Query(Constants.API.Body.Field.ACCESS_CODE) access_code: String
    ): Flowable<retrofit2.Response<BaseResponse>>

    /**
     * This method resets the password of the user account
     *
     * @param email email of the user
     * @return response consists of success status, data and message
     * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @POST(Constants.API.FORGOT_PASSWORD)
    fun resetPassword(@Query(Constants.API.Body.Field.EMAIL) email: String)
            : Flowable<retrofit2.Response<BaseResponse>>


    /**
     * This method logs out the user
     * @param accessToken access token of the user
     * @param deviceType type of the device
     * @param token FCM token of device in order to send push notifications in future
     * @return response consists of success status
     * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @GET(Constants.API.LOG_OUT)
    fun logOut(@Header(Constants.API.Header.Field.AUTHORIZATION) accessToken: String,
               @Query(Constants.API.Body.Field.DEVICE_TYPE) deviceType: Int,
               @Query(Constants.API.Body.Field.FCM_TOKEN) token: String)
            : Flowable<retrofit2.Response<BaseResponse>>

    /**
     * This method gets profile of the user
     * @param accessToken access token of the user
     * @return response consists of user profile
     * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @GET(Constants.API.PROFILE_DETAILS)
    fun getUserProfile(@Header(Constants.API.Header.Field.AUTHORIZATION) accessToken: String)
            : Flowable<retrofit2.Response<ProfileResponse>>



    /*
    * This method get the wallet list of user
    *
    * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @GET(Constants.API.WALLET_LIST)
    fun getWalletList(@Header(Constants.API.Header.Field.AUTHORIZATION) accessToken: String):
            Flowable<Response<MyWalletResponse>>


    /*
    * This method create wallet for a user
    * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @POST(Constants.API.CREATE_WALLET)
    fun createWallet(@Header(Constants.API.Header.Field.AUTHORIZATION) accessToken: String,
                     @Query(Constants.API.Body.Field.WALLET_NAME) walletName: String):
            Flowable<Response<BaseResponse>>


    /*
    * This method generate new address for a wallet
    * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @POST(Constants.API.GENERATE_WALLET_ADDRESS)
    fun generateWalletAddress(@Header(Constants.API.Header.Field.AUTHORIZATION) accessToken: String,
                              @Query(Constants.API.Body.Field.WALLET_ID) walletId: Int
    ): Flowable<Response<GenerateWalletAddressReponse>>


    /*
    * This method withdraw balance from a wallet
    * */

    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @POST(Constants.API.WITHDRAW_BALANCE)
    fun withdrawBalance(@Header(Constants.API.Header.Field.AUTHORIZATION) accessToken: String,
                        @Query(Constants.API.Body.Field.WALLET_ID) walletId: Int,
                        @Query(Constants.API.Body.Field.ADDRESS) walletAddress: String,
                        @Query(Constants.API.Body.Field.AMOUNT) amount: Double
    ): Flowable<Response<BaseResponse>>


    /*
    * This method get's my referral link and list
    * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @GET(Constants.API.MY_REFERRAL)
    fun myReferral(@Header(Constants.API.Header.Field.AUTHORIZATION) accessToken: String)
            : Flowable<Response<MyReferral>>

    /*
    * This method get a single wallet transaction history
    * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @GET
    fun getWalletHistory(@Header(Constants.API.Header.Field.AUTHORIZATION) accessToken: String,
                         @Url url: String
    ): Flowable<Response<WalletTransaction>>


    /*
    * This method get bank list from server
    * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @GET(Constants.API.BANK_LIST)
    fun getBankList(@Header(Constants.API.Header.Field.AUTHORIZATION) accessToken: String):
            Flowable<Response<BankList>>

    /*
* This function update user info
* */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @Multipart
    @POST(Constants.API.UPDATE_USER_PROFILE)
    fun updateProfile(
            @Header(Constants.API.Header.Field.AUTHORIZATION) accessToken: String,
            @Part(Constants.API.Body.Field.FIRST_NAME) firstName: RequestBody,
            @Part(Constants.API.Body.Field.LAST_NAME) lastName: RequestBody,
            @Part(Constants.API.Body.Field.COUNTRY) country: RequestBody,
            @Part(Constants.API.Body.Field.MOBILE) mobile: RequestBody,
            @Part imagePart: MultipartBody.Part?
    ): Flowable<Response<ProfileResponse>>


    /*
    * This method buy coin
    * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @Multipart
    @POST(Constants.API.BUY_COIN)
    fun buyCoin(@Header(Constants.API.Header.Field.AUTHORIZATION) accessToken: String,
                @Query(Constants.API.Body.Field.PAYMENT_TYPE) paymentType: RequestBody,
                @Query(Constants.API.Body.Field.COIN) coin: Double,
                @Query(Constants.API.Body.Field.PAYMENT_METHOD_NONCE) paymentNonce: RequestBody?,
                @Query(Constants.API.Body.Field.BANK_ID) bankId: RequestBody?,
                @Part sleep: MultipartBody.Part?
    ): Flowable<Response<BuyCoin>>

    /*
    * This function change current password
    * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @POST(Constants.API.CHANGE_PASSWORD)
    fun changePassword(@Header(Constants.API.Header.Field.AUTHORIZATION) accessToken: String,
                       @Query(Constants.API.Body.Field.CURRENT_PASS) currentPass: String,
                       @Query(Constants.API.Body.Field.NEW_PASSWOD) newPass: String,
                       @Query(Constants.API.Body.Field.REPEAT_PASSWORD) repeatPassword: String
    ): Flowable<Response<ChangePassword>>



    /*
    * This method get's home data from server
    * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @GET(Constants.API.HOME)
    fun getHomeData(@Header(Constants.API.Header.Field.AUTHORIZATION) accessToken: String,
                    @Query(Constants.API.Body.Field.TIME_PREIOD) time: String
    ): Flowable<Response<HomeData>>


    /*
    * This method reset password
    * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @POST(Constants.API.RESET_PASSWORD)
    fun resetPassword(@Query(Constants.API.Body.Field.EMAIL) email: String,
                      @Query(Constants.API.Body.Field.ACCESS_CODE) access_code: String,
                      @Query(Constants.API.Body.Field.PASSWORD) password: String,
                      @Query(Constants.API.Body.Field.CONFRIM_PASSWORD) confirmPassword: String
    ): Flowable<Response<BaseResponse>>


    companion object {
        private val gson = GsonBuilder().setLenient().create()
        private var httpLogger = HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        private val customClient = OkHttpClient.Builder()
                .addInterceptor(httpLogger)
                .addInterceptor(ConnectivityInterceptor())
                .build()
        private val sRetrofitBuilder = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(customClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(DataUtils.getString(R.string.api_base_url))
                .build()

        private var sInstance: ApiService? = null
        /**
         * This method returns an instance of the this service
         *
         * @return instance of the this service
         * */
        fun on(): ApiService {
            if (sInstance == null) {
                sInstance = sRetrofitBuilder.create(ApiService::class.java)
            }
            return sInstance!!
        }
    }
}