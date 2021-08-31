package com.wallet.cryptofuelx.main.ui.app.userprofile

import com.wallet.cryptofuelx.R
import com.wallet.cryptofuelx.main.data.BaseRepository
import com.wallet.cryptofuelx.main.ui.base.component.BasePresenter
import com.wallet.cryptofuelx.main.ui.base.helper.ProgressDialogUtils
import com.wallet.cryptofuelx.utils.helper.Constants
import com.wallet.cryptofuelx.utils.helper.DataUtils
import com.wallet.cryptofuelx.utils.helper.network.NoConnectivityException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File

class UserProfilePresenter : BasePresenter<UserProfileView>() {


    fun getUserProfileInfo() {
        ProgressDialogUtils.on().showProgressDialog(activity!!)
        compositeDisposable.add(
                BaseRepository.on().getUserProfile()
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
                                            mvpView?.getProfileDataSuccess(baseResponse)
                                        } else {
                                            mvpView?.getProfileDataError(baseResponse!!.message)
                                        }
                                    } else {
                                        mvpView?.getProfileDataError(DataUtils.getString(R.string.error_getting_user_info))
                                    }
                                } else {
                                    mvpView?.getProfileDataError(DataUtils.getString(R.string.error_getting_user_info))
                                }
                            }
                        }, {
                            if (it is NoConnectivityException) {
                                mvpView?.getProfileDataError(DataUtils.getString(R.string.error_internet_connection))
                            } else {
                                mvpView?.getProfileDataError(DataUtils.getString(R.string.error_getting_user_info))
                            }
                        })
        )
    }

    fun updateUserProfile(firstName: String,
                          lastName: String,
                          phone: String,
                          country: String,
                          image: File?
    ) {
        ProgressDialogUtils.on().showProgressDialog(activity!!)
        compositeDisposable.add(BaseRepository.on().updateProfile(
                firstName,
                lastName,
                country,
                phone,
                image
        ).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    if (it.code() == Constants.HTTP_CODE.UNAUTHENTICATED) {
                        BaseRepository.on().logOut(activity!!, false)
                    } else {
                        if (it.isSuccessful) {
                            val baseResponse = it.body()
                            if (baseResponse != null) {
                                if (baseResponse!!.success) {
                                    mvpView?.onProfileUpdateSuccess(baseResponse!!)
                                } else {
                                    mvpView?.onProfileUpdateError(baseResponse!!.message)
                                }
                            } else {
                                mvpView?.onProfileUpdateError(DataUtils.getString(R.string.error_updating_profile))
                            }
                        } else {
                            mvpView?.onProfileUpdateError(DataUtils.getString(R.string.error_updating_profile))
                        }
                    }
                }, {
                    if (it is NoConnectivityException) {
                        mvpView?.onProfileUpdateError(DataUtils.getString(R.string.error_internet_connection))
                    } else {
                        mvpView?.onProfileUpdateError(DataUtils.getString(R.string.error_updating_profile))
                    }
                })

        )
    }

}