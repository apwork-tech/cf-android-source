package com.wallet.cryptofuelx.main.ui.app.landing.deposit

import android.graphics.Paint
import com.wallet.cryptofuelx.R
import com.wallet.cryptofuelx.main.ui.base.component.BaseFragment
import com.wallet.cryptofuelx.main.ui.base.helper.ProgressDialogUtils
import com.wallet.cryptofuelx.main.ui.base.setRipple
import com.wallet.cryptofuelx.utils.helper.Constants
import com.wallet.cryptofuelx.utils.helper.DataUtils
import com.wallet.cryptofuelx.utils.libs.ToastUtils
import kotlinx.android.synthetic.main.fragment_deposit.*

class DepositFragment : BaseFragment<DepositView, DepositPresenter>(), DepositView {

    override val layoutId: Int
        get() = R.layout.fragment_deposit

    override fun getFragmentPresenter(): DepositPresenter {
        return DepositPresenter()
    }

    override fun startUI() {
        tv_generate_new_address.paintFlags = tv_generate_new_address.paintFlags or
                Paint.UNDERLINE_TEXT_FLAG
        tv_copy.setRipple(R.color.colorWhite50)
        setListener()
        //set default value to address
        tv_wallet_address?.text = arguments?.getString(Constants.IntentKeys.WALLET_ADDRESS)
        if(arguments?.getString(Constants.IntentKeys.WALLET_ADDRESS)!!.isNotEmpty()){
            image_view_qr_code?.setImageBitmap(DataUtils.getQRCodeBitMapFromString(
                    arguments?.getString(Constants.IntentKeys.WALLET_ADDRESS)!!
            ))
        }

        tv_btc_balance?.text = arguments?.getString(Constants.IntentKeys.BTC_BALANCE)
        tv_secondary_balance?.text = arguments?.getString(Constants.IntentKeys.USD_BALANCE)
    }

    private fun setListener() {
        tv_generate_new_address?.setOnClickListener {
            presenter.generateNewWalletAddress(
                    arguments?.getInt(Constants.IntentKeys.WALLET_ID)!!)
        }
        tv_copy.setOnClickListener {
            DataUtils.copyTextToClipBoard(activity!!, tv_wallet_address?.text.toString())
            ToastUtils.success(getString(R.string.txt_wallet_address_copied))
        }
    }

    override fun stopUI() {

    }

    override fun generateWalletSuccess(walletList: List<String>) {
        ProgressDialogUtils.on().hideProgressDialog()
        tv_wallet_address?.text = walletList[0]
        image_view_qr_code?.setImageBitmap(DataUtils.getQRCodeBitMapFromString(walletList[0]))
    }

    override fun generateWalletError(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.error(message)
    }


}