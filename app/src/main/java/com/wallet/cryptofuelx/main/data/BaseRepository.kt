package com.wallet.cryptofuelx.main.data

import android.app.Activity
import android.content.Context
import com.wallet.cryptofuelx.R
import com.wallet.cryptofuelx.main.data.local.AppLocalDataSource
import com.wallet.cryptofuelx.main.data.remote.AppRemoteDataSource
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
import com.wallet.cryptofuelx.main.ui.app.authentication.login.LoginActivity
import com.wallet.cryptofuelx.main.ui.base.helper.ProgressDialogUtils
import com.wallet.cryptofuelx.utils.helper.DataUtils
import com.wallet.cryptofuelx.utils.libs.ToastUtils
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import timber.log.Timber
import java.io.File

/**
 * This is the repository class of the project. This class contains all the basic methods needed
 * for the project purposes.
 * @author Mohd. Asfaq-E-Azam Rifat
 */
class BaseRepository(context: Context) {
    private val mAppLocalDataSource = AppLocalDataSource(context)
    private val mAppRemoteDataSource = AppRemoteDataSource(context)

    companion object {
        private lateinit var sInstance: BaseRepository

        /**
         * This method returns an instance of the this class
         *
         * @return instance of the this class
         * */
        fun on(): BaseRepository {
            return sInstance
        }

        /**
         * This method initializes the class
         * @param context application context
         * */
        @Synchronized
        fun init(context: Context) {
            synchronized(BaseRepository::class.java) {
                sInstance = BaseRepository(context)
            }
        }
    }


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
                 email: String, mobilePhone: String, password: String, confirmPassword: String)
            : Flowable<retrofit2.Response<BaseResponse>> {
        return mAppRemoteDataSource.register(name, lastName,
                email, mobilePhone, password, confirmPassword)
    }

    /**
     * This method logs in an user
     *
     * @param email email of the user
     * @param password password of the user
     * @param token FCM token of device in order to send push notifications in future
     * @return response consists of success status, data and message
     * */
    fun login(email: String, password: String): Flowable<retrofit2.Response<LoginResponse>> {
        return mAppRemoteDataSource.login(email, password)
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
        return mAppRemoteDataSource.verifyEmail(
                email,
                access_code
        )
    }

    /**
     * This method resets the password of the user account
     *
     * @param email email of the user
     * @return response consists of success status, data and message
     * */
    fun resetPassword(email: String): Flowable<retrofit2.Response<BaseResponse>> {
        return mAppRemoteDataSource.resetPassword(email)
    }


    /**
     * This method logs out the user
     * @param activity current activity
     * @param eventFromDrawer if it's called from the navigation drawer or not
     * @return disposable operation
     * */
    fun logOut(activity: Activity, eventFromDrawer: Boolean = false): Disposable {
        ProgressDialogUtils.on().showProgressDialog(activity)
        return mAppRemoteDataSource.logOut()
                .flatMapCompletable {
                    mAppLocalDataSource.logOut()
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(object : DisposableCompletableObserver() {
                    override fun onError(e: Throwable) {
                        ProgressDialogUtils.on().hideProgressDialog()
                        Timber.e(e)
                    }

                    override fun onComplete() {
                        ProgressDialogUtils.on().hideProgressDialog()
                        if (!eventFromDrawer) {
                            ToastUtils.warning(DataUtils.getString(R.string.session_expired))
                        }
                        LoginActivity.startActivity(activity)
                        activity.finish()
                    }
                })
    }


    /**
     * This method gets profile of the user
     * @return response consists of user profile
     * */
    fun getUserProfile(): Flowable<retrofit2.Response<ProfileResponse>> {
        return mAppRemoteDataSource.getUserProfile()
    }


    /*
   * This method get the wallet list of user
   *
   * */
    fun getWalletList():
            Flowable<Response<MyWalletResponse>> {
        return mAppRemoteDataSource.getWalletList()
    }


    /*
 * This method generate new address for a wallet
 * */
    fun generateWalletAddress(walletId: Int):
            Flowable<Response<GenerateWalletAddressReponse>> {
        return mAppRemoteDataSource.generateWalletAddress(walletId)
    }


    /*
* This method get bank list from server
* */
    fun getBankList(): Flowable<Response<BankList>> {
        return mAppRemoteDataSource.getBankList()
    }


    /*
    * This function withdraw balance from a wallet
    * */

    fun withdrawBalance(walletId: Int,
                        walletAddress: String,
                        amount: Double
    ): Flowable<Response<BaseResponse>> {
        return mAppRemoteDataSource.withdrawBalance(
                walletId,
                walletAddress,
                amount
        )
    }

    /*
   * This method get my referral url and list
   * */
    fun myReferral()
            : Flowable<Response<MyReferral>> {
        return mAppRemoteDataSource.myReferral()
    }

    /*
* This method get a single wallet transaction history
* */
    fun getWalletHistory(encriptedId: String): Flowable<Response<WalletTransaction>> {
        return mAppRemoteDataSource.getWalletHistory(encriptedId)
    }

    /*
* This method create wallet for a user
* */
    fun createWallet(walletName: String): Flowable<Response<BaseResponse>> {
        return mAppRemoteDataSource.createWallet(
                walletName
        )
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
        return mAppRemoteDataSource.updateProfile(
                firstName,
                lastName,
                country,
                mobile,
                image
        )
    }

    /*
* This function change current password
* */
    fun changePassword(currentPass: String,
                       newPass: String,
                       repeatPassword: String
    ): Flowable<Response<ChangePassword>> {
        return mAppRemoteDataSource.changePassword(
                currentPass,
                newPass,
                repeatPassword
        )
    }

    fun getHomeData(time: String): Flowable<Response<HomeData>> {
        return mAppRemoteDataSource.getHomeData(time)
    }

    /*
   * This method reset password
   * */
    fun resetPassword(email: String,
                      access_code: String,
                      password: String,
                      confirmPassword: String
    ): Flowable<Response<BaseResponse>> {
        return mAppRemoteDataSource.resetPassword(email, access_code, password, confirmPassword)
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
        return mAppRemoteDataSource.buyCoin(
                paymentType,
                bankId,
                coin,
                paymentMethodNonce,
                sleep
        )
    }

}
