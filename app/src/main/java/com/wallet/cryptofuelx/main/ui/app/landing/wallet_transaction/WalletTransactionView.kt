package com.wallet.cryptofuelx.main.ui.app.landing.wallet_transaction

import com.wallet.cryptofuelx.main.data.remote.response.wallet_transaction.Transaction
import com.wallet.cryptofuelx.main.ui.base.callback.MvpView

interface WalletTransactionView : MvpView {
    fun onWalletHistoryGetSuccess(transactionList: List<Transaction>)
    fun onWalletHistoryGetError(message: String)
}