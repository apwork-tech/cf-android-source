package com.wallet.cryptofuelx.main.ui.app.authentication.verification

import com.wallet.cryptofuelx.main.ui.base.callback.MvpView

interface CodeVerificationMvpView : MvpView {
    fun onSuccess(message: String)
    fun onError(message: String)
}