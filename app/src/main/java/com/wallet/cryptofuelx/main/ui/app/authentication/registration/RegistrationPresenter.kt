package com.wallet.cryptofuelx.main.ui.app.authentication.registration

import android.content.Context
import android.text.TextUtils
import com.wallet.cryptofuelx.R
import com.wallet.cryptofuelx.main.data.BaseRepository
import com.wallet.cryptofuelx.main.ui.base.component.BasePresenter
import com.wallet.cryptofuelx.main.ui.base.helper.ProgressDialogUtils
import com.wallet.cryptofuelx.utils.helper.DataUtils
import com.wallet.cryptofuelx.utils.helper.network.NoConnectivityException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*


class RegistrationPresenter : BasePresenter<RegistrationMvpView>() {

    private var mToken: String? = null

    fun registerTheUser(context: Context, name: String,
                        lastName: String,
                        email: String, mobilePhone: String, password: String,
                        confirmPassword: String
    ) {
        ProgressDialogUtils.on().showProgressDialog(context)
        compositeDisposable.add(
                BaseRepository.on().register(name, lastName,
                        email,
                        mobilePhone, password, confirmPassword)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            val baseResponse = it.body()
                            if (baseResponse == null) {
                                mvpView?.onError(DataUtils.getString(
                                        R.string.registration_error_of_registration))
                            } else {
                                if (baseResponse.isSuccessful) {
                                    mvpView?.onSuccess(String.format(Locale.getDefault(),
                                            DataUtils.getString(
                                                    R.string.registration_confirmation_of_registration),
                                            email))
                                } else {
                                    mvpView?.onError(baseResponse.message)
                                }
                            }
                        }, {
                            if (it is NoConnectivityException &&
                                    !TextUtils.isEmpty(it.message)) {
                                mvpView?.onError(it.message)
                            } else {
                                mvpView?.onError(DataUtils.getString(
                                        R.string.registration_error_of_registration))
                            }
                        }))
    }
}