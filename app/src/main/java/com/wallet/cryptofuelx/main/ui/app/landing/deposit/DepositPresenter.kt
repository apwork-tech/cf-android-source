package com.wallet.cryptofuelx.main.ui.app.landing.deposit

import com.wallet.cryptofuelx.R
import com.wallet.cryptofuelx.main.data.BaseRepository
import com.wallet.cryptofuelx.main.ui.base.component.BasePresenter
import com.wallet.cryptofuelx.main.ui.base.helper.ProgressDialogUtils
import com.wallet.cryptofuelx.utils.helper.Constants
import com.wallet.cryptofuelx.utils.helper.DataUtils
import com.wallet.cryptofuelx.utils.helper.network.NoConnectivityException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DepositPresenter : BasePresenter<DepositView>() {

    fun generateNewWalletAddress(walletId: Int) {
        ProgressDialogUtils.on().showProgressDialog(activity!!)
        compositeDisposable.add(
                BaseRepository.on().generateWalletAddress(
                        walletId
                ).observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            if (it.code() == Constants.HTTP_CODE.UNAUTHENTICATED) {
                                BaseRepository.on().logOut(activity!!, false)
                            } else {
                                if (it.isSuccessful) {
                                    val baseResponse = it.body()
                                    if (baseResponse != null) {
                                        if(baseResponse!!.success){
                                            mvpView?.generateWalletSuccess(baseResponse.addressList)
                                        }else{
                                            mvpView?.generateWalletError(baseResponse.message)
                                        }
                                    } else {
                                        mvpView?.generateWalletError(DataUtils.getString(R.string.error_generate_wallet_address))
                                    }
                                } else {
                                    mvpView?.generateWalletError(DataUtils.getString(R.string.error_generate_wallet_address))
                                }

                            }
                        }, {
                            if (it is NoConnectivityException) {
                                mvpView?.generateWalletError(DataUtils.getString(R.string.error_internet_connection))
                            } else {
                                mvpView?.generateWalletError(DataUtils.getString(R.string.error_generate_wallet_address))
                            }
                        })
        )
    }
}