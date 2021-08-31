package com.wallet.cryptofuelx.main.ui.app.landing.withdraw

import com.wallet.cryptofuelx.main.ui.base.callback.MvpView

interface WithdrawView : MvpView{
    fun onWithDrawSuccess(message:String)
    fun onWithDrawError(message: String)
}