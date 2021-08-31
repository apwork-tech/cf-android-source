package com.wallet.cryptofuelx.main.ui.app.authentication.verification

import com.wallet.cryptofuelx.R
import com.wallet.cryptofuelx.main.data.BaseRepository
import com.wallet.cryptofuelx.main.ui.base.component.BasePresenter
import com.wallet.cryptofuelx.utils.helper.DataUtils
import com.wallet.cryptofuelx.utils.helper.network.NoConnectivityException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CodeVerificationPresenter : BasePresenter<CodeVerificationMvpView>() {

    fun verifyEmail(email: String, accessCode: String) {
        compositeDisposable.add(
                BaseRepository.on().verifyEmail(
                        email,
                        accessCode)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            if (it.isSuccessful) {
                                val baseResponse = it.body()
                                if (baseResponse != null) {
                                    if (baseResponse.isSuccessful) {
                                        mvpView?.onSuccess(baseResponse.message)
                                    } else {
                                        mvpView?.onError(baseResponse.message)
                                    }
                                } else {
                                    mvpView?.onError(DataUtils.getString(R.string.error_email_verify))
                                }
                            } else {
                                mvpView?.onError(DataUtils.getString(R.string.error_email_verify))
                            }
                        }, {
                            if (it is NoConnectivityException) {
                                mvpView?.onError(DataUtils.getString(R.string.error_internet_connection))
                            } else {
                                mvpView?.onError(DataUtils.getString(R.string.error_email_verify))
                            }
                        })
        )
    }
}