package com.wallet.cryptofuelx.main.ui.app.landing.my_wallet

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.wallet.cryptofuelx.R
import com.wallet.cryptofuelx.main.ui.app.landing.container.ContainerActivity
import com.wallet.cryptofuelx.main.ui.base.callback.ItemClickListener
import com.wallet.cryptofuelx.main.ui.base.component.BaseFragment
import com.wallet.cryptofuelx.utils.helper.ViewUtils
import com.wallet.cryptofuelx.utils.libs.ToastUtils
import kotlinx.android.synthetic.main.fragment_deposit.*
import kotlinx.android.synthetic.main.fragment_my_wallet.*
import android.os.Bundle
import com.wallet.cryptofuelx.main.data.remote.response.my_wallet.MyWalletResponse
import com.wallet.cryptofuelx.main.data.remote.response.my_wallet.Wallet
import com.wallet.cryptofuelx.main.ui.base.helper.ProgressDialogUtils
import com.wallet.cryptofuelx.utils.helper.Constants
import com.wallet.cryptofuelx.utils.helper.SharedPrefUtils


class MyWalletFragment : BaseFragment<MyWalletView, MyWalletPresenter>(), MyWalletView {

    override val layoutId: Int
        get() = R.layout.fragment_my_wallet

    override fun getFragmentPresenter(): MyWalletPresenter {
        return MyWalletPresenter()
    }

    override fun startUI() {
        initializeMyWalletListAdapter()
        presenter.getMyWalletResponse()
    }


    private fun getMyWalletAdapter(): MyWalletAdapter {
        return recycler_view_wallet_list.adapter as MyWalletAdapter
    }

    private fun initializeMyWalletListAdapter() {
        ViewUtils.initializeRecyclerView(
                recycler_view_wallet_list,
                MyWalletAdapter(),
                object : ItemClickListener<Wallet> {
                    override fun onItemClick(view: View, item: Wallet) {
                        when (view.id) {
                            R.id.btn_deposit -> {
                                try {
                                    val bundle = Bundle()
                                    bundle.putInt(Constants.IntentKeys.WALLET_ID, item.id)
                                    bundle.putString(Constants.IntentKeys.WALLET_ADDRESS, item.address[0])
                                    bundle.putString(Constants.IntentKeys.USD_BALANCE, tv_secondary?.text.toString())
                                    bundle.putString(Constants.IntentKeys.BTC_BALANCE, tv_btc?.text.toString())
                                    (activity!! as ContainerActivity).visitDepositIntoWallet(bundle)
                                } catch (ex: IndexOutOfBoundsException) {
                                    ex.printStackTrace()
                                    val bundle = Bundle()
                                    bundle.putInt(Constants.IntentKeys.WALLET_ID, item.id)
                                    bundle.putString(Constants.IntentKeys.WALLET_ADDRESS, "")
                                    bundle.putString(Constants.IntentKeys.USD_BALANCE, tv_secondary?.text.toString())
                                    bundle.putString(Constants.IntentKeys.BTC_BALANCE, tv_btc?.text.toString())
                                    (activity!! as ContainerActivity).visitDepositIntoWallet(bundle)
                                }


                            }

                            R.id.btn_withdraw -> {
                                val bundle = Bundle()
                                bundle.putInt(Constants.IntentKeys.WALLET_ID, item.id)
                                bundle.putString(Constants.IntentKeys.USD_BALANCE, tv_secondary?.text.toString())
                                bundle.putString(Constants.IntentKeys.BTC_BALANCE, tv_btc?.text.toString())
                                (activity!! as ContainerActivity).visitWithDraw(bundle)
                            }

                            R.id.btn_activity -> {
                                val bundle = Bundle()
                                bundle.putString(Constants.IntentKeys.WALLET_ID, item.encryptId)
                                bundle.putString(Constants.IntentKeys.USD_BALANCE, tv_secondary?.text.toString())
                                bundle.putString(Constants.IntentKeys.BTC_BALANCE, tv_btc?.text.toString())
                                (activity!! as ContainerActivity).visitWalletTransaction(bundle)
                            }
                        }
                    }
                },
                null,
                LinearLayoutManager(activity!!),
                null,
                null,
                null,
                null
        )
    }

    override fun stopUI() {

    }


    override fun onMyWalletGetSuccess(myWalletResponse: MyWalletResponse) {
        ProgressDialogUtils.on().hideProgressDialog()
        tv_btc?.text = myWalletResponse.availableBalance.availableCoin.plus(myWalletResponse.availableBalance.coinName)
        tv_secondary?.text = "$".plus(myWalletResponse.availableBalance.availableUsd)
        SharedPrefUtils.write(Constants.IntentKeys.USD_BALANCE, "$".plus(myWalletResponse.availableBalance.availableUsd))
        SharedPrefUtils.write(Constants.IntentKeys.BTC_BALANCE, myWalletResponse.availableBalance.availableCoin.plus(myWalletResponse.availableBalance.coinName))
        getMyWalletAdapter().addItems(myWalletResponse.walletList)

    }

    override fun onMyWalletGetError(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.error(message)
    }


}