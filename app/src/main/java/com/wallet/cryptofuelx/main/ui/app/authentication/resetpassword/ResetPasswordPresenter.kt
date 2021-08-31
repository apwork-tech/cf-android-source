package com.wallet.cryptofuelx.main.ui.app.authentication.resetpassword

import com.wallet.cryptofuelx.R
import com.wallet.cryptofuelx.main.data.BaseRepository
import com.wallet.cryptofuelx.main.ui.base.component.BasePresenter
import com.wallet.cryptofuelx.main.ui.base.helper.ProgressDialogUtils
import com.wallet.cryptofuelx.utils.helper.Constants
import com.wallet.cryptofuelx.utils.helper.DataUtils
import com.wallet.cryptofuelx.utils.helper.network.NoConnectivityException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ResetPasswordPresenter : BasePresenter<ResetPasswordMvpView>() {

    fun resetPassword(email: String,
                      accessCode: String,
                      password: String,
                      confirmPassword: String
    ) {
        ProgressDialogUtils.on().showProgressDialog(activity!!)
        compositeDisposable.add(
                BaseRepository.on().resetPassword(
                        email,
                        accessCode,
                        password,
                        confirmPassword
                ).observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            if (it.code() == Constants.HTTP_CODE.UNAUTHENTICATED) {
                                BaseRepository.on().logOut(activity!!, false)
                            } else {
                                if (it.isSuccessful) {
                                    val baseResponse = it.body()
                                    if (baseResponse != null) {
                                        if (baseResponse.isSuccessful) {
                                            mvpView?.onSuccess(baseResponse.message)
                                        } else {
                                            mvpView?.onError(baseResponse.message)
                                        }
                                    } else {
                                        mvpView?.onError(DataUtils.getString(R.string.error_reset_password))
                                    }
                                } else {
                                    mvpView?.onError(DataUtils.getString(R.string.error_reset_password))
                                }
                            }
                        }, {
                            if (it is NoConnectivityException) {
                                mvpView?.onError(DataUtils.getString(R.string.error_internet_connection))
                            } else {
                                mvpView?.onError(DataUtils.getString(R.string.error_reset_password))
                            }
                        })
        )
    }
}