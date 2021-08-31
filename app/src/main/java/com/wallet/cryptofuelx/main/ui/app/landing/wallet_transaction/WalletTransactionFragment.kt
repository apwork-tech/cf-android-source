package com.wallet.cryptofuelx.main.ui.app.landing.wallet_transaction

import androidx.recyclerview.widget.LinearLayoutManager
import com.wallet.cryptofuelx.R
import com.wallet.cryptofuelx.main.data.remote.response.wallet_transaction.Transaction
import com.wallet.cryptofuelx.main.ui.base.component.BaseFragment
import com.wallet.cryptofuelx.main.ui.base.helper.ProgressDialogUtils
import com.wallet.cryptofuelx.utils.helper.Constants
import com.wallet.cryptofuelx.utils.helper.ViewUtils
import com.wallet.cryptofuelx.utils.libs.ToastUtils
import kotlinx.android.synthetic.main.fragment_wallet_transaction.*

class WalletTransactionFragment : BaseFragment<WalletTransactionView, WalletTransactionPresenter>(), WalletTransactionView {


    override val layoutId: Int
        get() = R.layout.fragment_wallet_transaction

    override fun getFragmentPresenter(): WalletTransactionPresenter {
        return WalletTransactionPresenter()
    }

    private fun initializeWalletTransactionList() {
        ViewUtils.initializeRecyclerView(
                recycler_view_wallet_transaction,
                WalletTransactionAdapter(),
                null,
                null,
                LinearLayoutManager(activity!!),
                null,
                null,
                null
        )
    }

    override fun startUI() {
        initializeWalletTransactionList()
        tv_btc_balance?.text = arguments?.getString(Constants.IntentKeys.BTC_BALANCE)
        tv_secondary_balance?.text = arguments?.getString(Constants.IntentKeys.USD_BALANCE)
        var walletEncryptedId = arguments?.getString(Constants.IntentKeys.WALLET_ID)
        presenter.getWalletHistory(walletEncryptedId!!)
    }

    override fun stopUI() {

    }

    private fun getWalletTransactionAdapter(): WalletTransactionAdapter {
        return recycler_view_wallet_transaction?.adapter as WalletTransactionAdapter
    }

    override fun onWalletHistoryGetSuccess(walletList: List<Transaction>) {
        ProgressDialogUtils.on().hideProgressDialog()
        getWalletTransactionAdapter().addItems(walletList)
    }

    override fun onWalletHistoryGetError(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.error(message)
    }

}