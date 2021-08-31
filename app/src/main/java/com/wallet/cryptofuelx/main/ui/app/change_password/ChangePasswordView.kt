package com.wallet.cryptofuelx.main.ui.app.change_password

import com.wallet.cryptofuelx.main.ui.base.callback.MvpView


interface ChangePasswordView : MvpView {
    fun onChangeSuccess(message: String)
    fun onChangeError(message: String)
}