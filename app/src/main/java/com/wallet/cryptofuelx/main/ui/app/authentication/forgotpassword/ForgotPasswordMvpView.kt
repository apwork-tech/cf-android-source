package com.wallet.cryptofuelx.main.ui.app.authentication.forgotpassword

import com.wallet.cryptofuelx.main.ui.base.callback.MvpView

interface ForgotPasswordMvpView : MvpView {
    fun onSuccess(message: String)
    fun onError(message: String)
}