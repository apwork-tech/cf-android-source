package com.wallet.cryptofuelx.main.ui.app.authentication.login

import com.wallet.cryptofuelx.main.ui.base.callback.MvpView

interface LoginMvpView : MvpView {
    fun onSuccess(message: String)
    fun onError(message: String)
}