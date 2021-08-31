package com.wallet.cryptofuelx.main.ui.app.authentication.registration

import com.wallet.cryptofuelx.main.ui.base.callback.MvpView

interface RegistrationMvpView : MvpView {
    fun onSuccess(message: String)
    fun onError(message: String)
}