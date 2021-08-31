package com.wallet.cryptofuelx.main.ui.app.landing.deposit

import com.wallet.cryptofuelx.main.ui.base.callback.MvpView

interface DepositView : MvpView {
    fun generateWalletSuccess(walletList: List<String>)
    fun generateWalletError(message: String)
}