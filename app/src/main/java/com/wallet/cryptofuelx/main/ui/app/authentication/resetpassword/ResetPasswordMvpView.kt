package com.wallet.cryptofuelx.main.ui.app.authentication.resetpassword

import com.wallet.cryptofuelx.main.ui.base.callback.MvpView

interface ResetPasswordMvpView : MvpView {
    fun onSuccess(message: String)
    fun onError(message: String)
}