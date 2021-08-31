package com.wallet.cryptofuelx.main.ui.app.landing.buy_coin

import com.wallet.cryptofuelx.R
import com.wallet.cryptofuelx.main.data.BaseRepository
import com.wallet.cryptofuelx.main.ui.base.component.BasePresenter
import com.wallet.cryptofuelx.main.ui.base.helper.ProgressDialogUtils
import com.wallet.cryptofuelx.utils.helper.Constants
import com.wallet.cryptofuelx.utils.helper.DataUtils
import com.wallet.cryptofuelx.utils.helper.network.NoConnectivityException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.io.File

class BuyCoinPresenter : BasePresenter<BuyCoinView>() {

    fun getBankList() {
        ProgressDialogUtils.on().showProgressDialog(activity!!)
        compositeDisposable.add(
                BaseRepository.on().getBankList()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            if (it.code() == Constants.HTTP_CODE.UNAUTHENTICATED) {
                                BaseRepository.on().logOut(activity!!, false)
                            } else {
                                if (it.isSuccessful) {
                                    val baseResponse = it.body()
                                    if (baseResponse != null) {
                                        if (baseResponse.success) {
                                            mvpView?.onBankListGetSuccess(baseResponse!!)
                                        } else {
                                            mvpView?.onBankListGetError(baseResponse!!.message)
                                        }
                                    } else {
                                        mvpView?.onBankListGetError(DataUtils.getString(R.string.error_bank_list))
                                    }
                                } else {
                                    mvpView?.onBankListGetError(DataUtils.getString(R.string.error_bank_list))
                                }
                            }
                        }, {
                            Timber.e(it)
                            if (it is NoConnectivityException) {
                                mvpView?.onBankListGetError(DataUtils.getString(R.string.error_internet_connection))
                            } else {
                                mvpView?.onBankListGetError(DataUtils.getString(R.string.error_bank_list))
                            }
                        })
        )
    }

    fun buyCoin(paymentType: Int,
                bankId: Int?,
                coin: Double,
                paymentMethodNonce: String?,
                sleep: File?) {
        compositeDisposable.add(
                BaseRepository.on().buyCoin(
                        paymentType,
                        bankId,
                        coin,
                        paymentMethodNonce,
                        sleep
                ).observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            if (it.code() == Constants.HTTP_CODE.UNAUTHENTICATED) {
                                BaseRepository.on().logOut(activity!!, false)
                            } else {
                                if (it.isSuccessful) {
                                    if (it.body() != null) {
                                        val baseResponse = it.body()
                                        if (baseResponse!!.success) {
                                            mvpView?.onBuyCoinSuccess(baseResponse)
                                        } else {
                                            mvpView?.onBuyCoinError(baseResponse.message)
                                        }
                                    } else {
                                        mvpView?.onBuyCoinError(DataUtils.getString(R.string.error_buy_coin))
                                    }
                                } else {
                                    mvpView?.onBuyCoinError(DataUtils.getString(R.string.error_buy_coin))
                                }
                            }
                        }, {
                            if (it is NoConnectivityException) {
                                mvpView?.onBuyCoinError(DataUtils.getString(R.string.error_internet_connection))
                            } else {
                                mvpView?.onBuyCoinError(DataUtils.getString(R.string.error_buy_coin))
                            }
                        })
        )
    }
}