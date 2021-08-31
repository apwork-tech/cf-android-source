package com.wallet.cryptofuelx.main.ui.app.landing.wallet_transaction

import com.wallet.cryptofuelx.R
import com.wallet.cryptofuelx.main.data.BaseRepository
import com.wallet.cryptofuelx.main.ui.base.component.BasePresenter
import com.wallet.cryptofuelx.main.ui.base.helper.ProgressDialogUtils
import com.wallet.cryptofuelx.utils.helper.Constants
import com.wallet.cryptofuelx.utils.helper.DataUtils
import com.wallet.cryptofuelx.utils.helper.network.NoConnectivityException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class WalletTransactionPresenter : BasePresenter<WalletTransactionView>() {

    fun getWalletHistory(encriptedId: String) {
        ProgressDialogUtils.on().showProgressDialog(activity!!)
        compositeDisposable.add(
                BaseRepository.on().getWalletHistory(
                        encriptedId
                ).observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            if (it.code() == Constants.HTTP_CODE.UNAUTHENTICATED) {
                                BaseRepository.on().logOut(activity!!, false)
                            } else {
                                if (it.isSuccessful) {
                                    val baseResponse = it.body()
                                    if (baseResponse != null) {
                                        //update the wallet history
                                        mvpView?.onWalletHistoryGetSuccess(baseResponse.transactionList)
                                    } else {
                                        mvpView?.onWalletHistoryGetError(DataUtils.getString(R.string.error_wallet_transaction))
                                    }
                                } else {
                                    mvpView?.onWalletHistoryGetError(DataUtils.getString(R.string.error_wallet_transaction))
                                }
                            }
                        }, {
                            if (it is NoConnectivityException) {
                                mvpView?.onWalletHistoryGetError(DataUtils.getString(R.string.error_internet_connection))
                            } else {
                                mvpView?.onWalletHistoryGetError(DataUtils.getString(R.string.error_wallet_transaction))
                            }
                        })
        )
    }

}