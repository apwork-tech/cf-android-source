package com.wallet.cryptofuelx.main.ui.app.landing.buy_coin

import com.wallet.cryptofuelx.main.data.remote.response.bank_list.BankList
import com.wallet.cryptofuelx.main.data.remote.response.buy_coin.BuyCoin
import com.wallet.cryptofuelx.main.ui.base.callback.MvpView

interface BuyCoinView : MvpView {
    fun onBankListGetSuccess(bankList: BankList)
    fun onBankListGetError(message: String)
    fun onBuyCoinSuccess(buyCoin:BuyCoin)
    fun onBuyCoinError(message: String)
}