package com.wallet.cryptofuelx.main.ui.app.landing.my_wallet

import com.wallet.cryptofuelx.main.data.remote.response.my_wallet.MyWalletResponse
import com.wallet.cryptofuelx.main.ui.base.callback.MvpView

interface MyWalletView : MvpView {
    fun onMyWalletGetSuccess(myWalletResponse: MyWalletResponse)
    fun onMyWalletGetError(message: String)
}