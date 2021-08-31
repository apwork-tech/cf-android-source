package com.wallet.cryptofuelx.main.ui.app.landing.dashboard

import android.text.TextUtils
import com.wallet.cryptofuelx.R
import com.wallet.cryptofuelx.main.data.BaseRepository
import com.wallet.cryptofuelx.main.ui.base.component.BasePresenter
import com.wallet.cryptofuelx.main.ui.base.helper.ProgressDialogUtils
import com.wallet.cryptofuelx.utils.helper.DataUtils
import com.wallet.cryptofuelx.utils.helper.network.NoConnectivityException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class DashboardPresenter : BasePresenter<DashboardMvpView>() {
    fun getHomeData(time: String, type:Int) {
        ProgressDialogUtils.on().showProgressDialog(activity!!)
        compositeDisposable.add(
                BaseRepository.on().getHomeData(time)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            if (it.code() == DataUtils.getInteger(R.integer.unauthenticated_code)) {
                                BaseRepository.on().logOut(activity!!, false)
                            } else {
                                val response = it.body()
                                if (response != null) {
                                    if (response.success) {
                                        mvpView?.onHomeDataSuccess(response, type)
                                    } else {
                                        mvpView?.onHomeDataError(response.message)
                                    }
                                } else {
                                    mvpView?.onHomeDataError(DataUtils.getString(R.string.error_dashboard))
                                }
                            }
                        }, {
                            Timber.d(it)
                            if (it is NoConnectivityException && !TextUtils.isEmpty(it.message)) {
                                mvpView?.onHomeDataError(DataUtils.getString(R.string.error_internet_connection))
                            } else {
                                mvpView?.onHomeDataError(DataUtils.getString(R.string.error_dashboard))
                            }
                        }))
    }
}