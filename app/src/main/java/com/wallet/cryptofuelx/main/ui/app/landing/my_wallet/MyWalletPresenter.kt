package com.wallet.cryptofuelx.main.ui.app.landing.my_wallet

import com.wallet.cryptofuelx.R
import com.wallet.cryptofuelx.main.data.BaseRepository
import com.wallet.cryptofuelx.main.ui.base.component.BasePresenter
import com.wallet.cryptofuelx.main.ui.base.helper.ProgressDialogUtils
import com.wallet.cryptofuelx.utils.helper.Constants
import com.wallet.cryptofuelx.utils.helper.DataUtils
import com.wallet.cryptofuelx.utils.helper.network.NoConnectivityException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MyWalletPresenter : BasePresenter<MyWalletView>() {
    fun getMyWalletResponse() {
        ProgressDialogUtils.on().showProgressDialog(activity!!)
        compositeDisposable.add(
                BaseRepository.on().getWalletList()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            if (it.code() == Constants.HTTP_CODE.UNAUTHENTICATED) {
                                BaseRepository.on().logOut(activity!!, false)
                            } else {
                                if (it.isSuccessful) {
                                    if (it.body() != null) {
                                        val baseResponse = it.body()
                                        if (baseResponse!!.success) {
                                            mvpView?.onMyWalletGetSuccess(baseResponse!!)
                                        } else {
                                            mvpView?.onMyWalletGetError(baseResponse!!.message)
                                        }
                                    } else {
                                        mvpView?.onMyWalletGetError(DataUtils.getString(R.string.error_my_wallet_data))
                                    }
                                } else {
                                    mvpView?.onMyWalletGetError(DataUtils.getString(R.string.error_my_wallet_data))
                                }
                            }
                        }, {
                            if (it is NoConnectivityException) {
                                mvpView?.onMyWalletGetError(DataUtils.getString(R.string.error_internet_connection))
                            } else {
                                mvpView?.onMyWalletGetError(DataUtils.getString(R.string.error_my_wallet_data))
                            }
                        })
        )
    }
}