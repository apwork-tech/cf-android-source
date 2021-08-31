package com.wallet.cryptofuelx.main.ui.app.add_wallet

import com.wallet.cryptofuelx.R
import com.wallet.cryptofuelx.main.data.BaseRepository
import com.wallet.cryptofuelx.main.ui.base.component.BasePresenter
import com.wallet.cryptofuelx.main.ui.base.helper.ProgressDialogUtils
import com.wallet.cryptofuelx.utils.helper.Constants
import com.wallet.cryptofuelx.utils.helper.DataUtils
import com.wallet.cryptofuelx.utils.helper.network.NoConnectivityException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AddWalletPresenter : BasePresenter<AddWalletView>() {
    fun createWallet(walletName: String) {
        ProgressDialogUtils.on().showProgressDialog(activity!!)
        compositeDisposable.add(
                BaseRepository.on().createWallet(
                        walletName
                ).observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            if (it.code() == Constants.HTTP_CODE.UNAUTHENTICATED) {
                                BaseRepository.on().logOut(
                                        activity!!,
                                        false
                                )
                            } else {
                                if (it.isSuccessful) {
                                    val baseResponse = it.body()
                                    if (baseResponse != null) {
                                        if(baseResponse!!.isSuccessful){
                                            mvpView?.onWalletCreateSuccess(baseResponse.message)
                                        }else{
                                            mvpView?.onWalletCreateError(baseResponse!!.message)
                                        }
                                    } else {
                                        mvpView?.onWalletCreateError(DataUtils.getString(R.string.error_create_wallet))
                                    }
                                } else {
                                    mvpView?.onWalletCreateError(DataUtils.getString(R.string.error_create_wallet))
                                }
                            }
                        }, {
                            if (it is NoConnectivityException) {
                                mvpView?.onWalletCreateError(DataUtils.getString(R.string.error_internet_connection))
                            } else {
                                mvpView?.onWalletCreateError(DataUtils.getString(R.string.error_create_wallet))
                            }
                        })
        )
    }
}