package com.wallet.cryptofuelx.main.ui.app.change_password

import com.wallet.cryptofuelx.R
import com.wallet.cryptofuelx.main.data.BaseRepository
import com.wallet.cryptofuelx.main.ui.base.component.BasePresenter
import com.wallet.cryptofuelx.main.ui.base.helper.ProgressDialogUtils
import com.wallet.cryptofuelx.utils.helper.Constants
import com.wallet.cryptofuelx.utils.helper.DataUtils
import com.wallet.cryptofuelx.utils.helper.network.NoConnectivityException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ChangePasswordPresenter : BasePresenter<ChangePasswordView>() {
    fun changePassword(currentPassword: String,
                       newPass: String,
                       confirmPass: String
    ) {
        ProgressDialogUtils.on().showProgressDialog(activity!!)
        compositeDisposable.add(
                BaseRepository.on().changePassword(
                        currentPassword,
                        newPass,
                        confirmPass
                ).observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            if (it.code() == Constants.HTTP_CODE.UNAUTHENTICATED) {
                                BaseRepository.on().logOut(activity!!, false)
                            } else {
                                if (it.isSuccessful) {
                                    val baseResponse = it.body()
                                    if (baseResponse != null) {
                                        if(baseResponse!!.status){
                                            mvpView?.onChangeSuccess(baseResponse!!.message)
                                        }else{
                                            mvpView?.onChangeError(baseResponse!!.message)
                                        }
                                    } else {
                                        mvpView?.onChangeError(DataUtils.getString(R.string.error_change_pass))
                                    }
                                } else {
                                    mvpView?.onChangeError(DataUtils.getString(R.string.error_change_pass))
                                }
                            }
                        }, {
                            if (it is NoConnectivityException) {
                                mvpView?.onChangeError(DataUtils.getString(R.string.error_internet_connection))
                            } else {
                                mvpView?.onChangeError(DataUtils.getString(R.string.error_change_pass))
                            }
                        })
        )
    }
}