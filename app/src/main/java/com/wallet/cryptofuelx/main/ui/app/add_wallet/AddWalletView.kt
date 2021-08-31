package com.wallet.cryptofuelx.main.ui.app.add_wallet

import com.wallet.cryptofuelx.main.ui.base.callback.MvpView

interface AddWalletView : MvpView {
    fun onWalletCreateSuccess(status: String)
    fun onWalletCreateError(message: String)
}