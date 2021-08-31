package com.wallet.cryptofuelx.main.ui.app.referral

import com.wallet.cryptofuelx.R
import com.wallet.cryptofuelx.main.data.BaseRepository
import com.wallet.cryptofuelx.main.ui.base.component.BasePresenter
import com.wallet.cryptofuelx.main.ui.base.helper.ProgressDialogUtils
import com.wallet.cryptofuelx.utils.helper.Constants
import com.wallet.cryptofuelx.utils.helper.DataUtils
import com.wallet.cryptofuelx.utils.helper.network.NoConnectivityException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ReferralPresenter : BasePresenter<ReferralView>() {

    fun getMyReferral() {
        ProgressDialogUtils.on().showProgressDialog(activity!!)
        compositeDisposable.add(
                BaseRepository.on().myReferral()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            if (it.code() == Constants.HTTP_CODE.UNAUTHENTICATED) {
                                BaseRepository.on().logOut(activity!!, false)
                            } else {
                                if (it.isSuccessful) {
                                    val baseResponse = it.body()
                                    if (baseResponse != null) {
                                        if (baseResponse!!.success) {
                                            mvpView?.onReferralGetSucces(baseResponse!!)
                                        } else {
                                            mvpView?.onReferralGetError(baseResponse!!.message)
                                        }
                                    } else {
                                        mvpView?.onReferralGetError(DataUtils.getString(R.string.error_my_referral))
                                    }
                                } else {
                                    mvpView?.onReferralGetError(DataUtils.getString(R.string.error_my_referral))
                                }
                            }
                        }, {
                            if (it is NoConnectivityException) {
                                mvpView?.onReferralGetError(DataUtils.getString(R.string.error_internet_connection))
                            } else {
                                mvpView?.onReferralGetError(DataUtils.getString(R.string.error_my_referral))
                            }
                        })
        )
    }

}