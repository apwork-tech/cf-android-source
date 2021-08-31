package com.wallet.cryptofuelx.main.ui.app.authentication.login

import android.content.Context
import android.text.TextUtils
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.wallet.cryptofuelx.R
import com.wallet.cryptofuelx.main.data.BaseRepository
import com.wallet.cryptofuelx.main.ui.app.authentication.verification.CodeVerificationActivity
import com.wallet.cryptofuelx.main.ui.base.component.BasePresenter
import com.wallet.cryptofuelx.main.ui.base.helper.ProgressDialogUtils
import com.wallet.cryptofuelx.utils.helper.Constants
import com.wallet.cryptofuelx.utils.helper.DataUtils
import com.wallet.cryptofuelx.utils.helper.SharedPrefUtils
import com.wallet.cryptofuelx.utils.helper.network.NoConnectivityException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class LoginPresenter : BasePresenter<LoginMvpView>() {
    private var mToken: String? = null

    fun login(context: Context, email: String, password: String) {
        ProgressDialogUtils.on().showProgressDialog(context)
        FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        mvpView?.onError(DataUtils.getString(R.string.login_error_login_failed))
                        return@OnCompleteListener
                    }
                    // Get new Instance ID token
                    mToken = task.result?.token
                    compositeDisposable.add(
                            BaseRepository.on().login(email, password)
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribeOn(Schedulers.io())
                                    .subscribe({
                                        val response = it.body()
                                        if (response == null) {
                                            mvpView?.onError(DataUtils.getString(R.string.login_error_login_failed))
                                        } else {
                                            if (response.isSuccessful) {
                                                if (response.data.asJsonObject.has(Constants.JsonKeys.ACCESS_TOKEN)) {
                                                    val accessToken = response.data.asJsonObject.get(Constants.JsonKeys.ACCESS_TOKEN).asString!!
                                                    SharedPrefUtils.write(Constants.PreferenceKeys.ACCESS_TOKEN, accessToken)

                                                    if (response.data.asJsonObject.has(Constants.JsonKeys.USER_INFO)) {
                                                        val userInfo = response.data.asJsonObject.get(Constants.JsonKeys.USER_INFO)!!

                                                        if (userInfo.asJsonObject.has(Constants.JsonKeys.NAME)) {
                                                            val userName = userInfo.asJsonObject.get(Constants.JsonKeys.NAME).asString!!
                                                            SharedPrefUtils.write(Constants.PreferenceKeys.NAME, userName)
                                                        }

                                                        if (userInfo.asJsonObject.has(Constants.JsonKeys.IMAGE)) {
                                                            val photo = userInfo.asJsonObject.get(Constants.JsonKeys.IMAGE).asString!!
                                                            SharedPrefUtils.write(Constants.PreferenceKeys.IMAGE, photo)
                                                        }

                                                        if (userInfo.asJsonObject.has(Constants.JsonKeys.EMAIL)) {
                                                            val userEmail = userInfo.asJsonObject.get(Constants.JsonKeys.EMAIL).asString!!
                                                            SharedPrefUtils.write(Constants.PreferenceKeys.EMAIL, userEmail)
                                                        }

                                                        if (userInfo.asJsonObject.has(Constants.JsonKeys.MOBILE)) {
                                                            val userMobile = userInfo.asJsonObject.get(Constants.JsonKeys.MOBILE).asString!!
                                                            SharedPrefUtils.write(Constants.PreferenceKeys.MOBILE, userMobile)
                                                        }

                                                        if (userInfo.asJsonObject.has(Constants.JsonKeys.FCM_TOKEN)) {
                                                            val fcmToken = userInfo.asJsonObject.get(Constants.JsonKeys.FCM_TOKEN).asString!!
                                                            SharedPrefUtils.write(Constants.PreferenceKeys.FCM_TOKEN, fcmToken)
                                                        }

                                                        SharedPrefUtils.write(Constants.PreferenceKeys.LOGGED_IN, true)
                                                        mvpView?.onSuccess(response.message)

                                                    } else {
                                                        mvpView?.onError(DataUtils.getString(R.string.login_error_login_failed))
                                                    }
                                                } else {
                                                    SharedPrefUtils.delete(Constants.PreferenceKeys.ACCESS_TOKEN)
                                                    mvpView?.onError(DataUtils.getString(R.string.login_error_login_failed))
                                                }
                                            } else {
                                                mvpView?.onError(response.message)
                                                //there is a bug i have to change the response by raw String response and check if is_verified field is exist or not
                                                if (!response.isVerified) {
                                                    CodeVerificationActivity.startActivity(context, email)
                                                }
                                            }
                                        }
                                    }, {
                                        Timber.d(it)
                                        if (it is NoConnectivityException && !TextUtils.isEmpty(it.message)) {
                                            mvpView?.onError(it.message)
                                        } else {
                                            mvpView?.onError(DataUtils.getString(R.string.login_error_login_failed))
                                        }
                                    }))
                })
    }
}