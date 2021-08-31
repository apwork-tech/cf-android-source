package com.wallet.cryptofuelx.main.ui.app.landing.withdraw

import com.wallet.cryptofuelx.R
import com.wallet.cryptofuelx.main.data.BaseRepository
import com.wallet.cryptofuelx.main.ui.base.component.BasePresenter
import com.wallet.cryptofuelx.main.ui.base.helper.ProgressDialogUtils
import com.wallet.cryptofuelx.utils.helper.Constants
import com.wallet.cryptofuelx.utils.helper.DataUtils
import com.wallet.cryptofuelx.utils.helper.network.NoConnectivityException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class WithdrawPresenter : BasePresenter<WithdrawView>() {


    fun withdrawBalance(walletId: Int,
                        walletAddress: String,
                        amount: Double
    ) {
        ProgressDialogUtils.on().showProgressDialog(activity!!)
        compositeDisposable.add(
                BaseRepository.on().withdrawBalance(
                        walletId,
                        walletAddress,
                        amount
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
                                            mvpView?.onWithDrawSuccess(baseResponse.message)
                                        } else {
                                            mvpView?.onWithDrawError(baseResponse.message)
                                        }
                                    } else {
                                        mvpView?.onWithDrawError(DataUtils.getString(R.string.error_withdraw))
                                    }
                                } else {
                                    mvpView?.onWithDrawError(DataUtils.getString(R.string.error_withdraw))
                                }
                            }
                        }, {
                            if (it is NoConnectivityException) {
                                mvpView?.onWithDrawError(DataUtils.getString(R.string.error_internet_connection))
                            } else {
                                mvpView?.onWithDrawError(DataUtils.getString(R.string.error_withdraw))
                            }
                        })
        )
    }

}