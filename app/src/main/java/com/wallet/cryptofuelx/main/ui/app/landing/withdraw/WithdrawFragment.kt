package com.wallet.cryptofuelx.main.ui.app.landing.withdraw

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import com.wallet.cryptofuelx.R
import com.wallet.cryptofuelx.main.ui.app.landing.container.ContainerActivity
import com.wallet.cryptofuelx.main.ui.base.component.BaseFragment
import com.wallet.cryptofuelx.main.ui.base.helper.ProgressDialogUtils
import com.wallet.cryptofuelx.main.ui.base.setRipple
import com.wallet.cryptofuelx.utils.helper.Constants
import com.wallet.cryptofuelx.utils.helper.PermissionUtils
import com.wallet.cryptofuelx.utils.helper.SharedPrefUtils
import com.wallet.cryptofuelx.utils.libs.ToastUtils
import kotlinx.android.synthetic.main.fragment_withdraw.*
import kotlinx.android.synthetic.main.fragment_withdraw.et_amount
import java.lang.NumberFormatException


class WithdrawFragment : BaseFragment<WithdrawView, WithdrawPresenter>(), WithdrawView {


    override val layoutId: Int
        get() = R.layout.fragment_withdraw

    override fun getFragmentPresenter(): WithdrawPresenter {
        return WithdrawPresenter()
    }


    override fun startUI() {
        btn_send.setRipple(R.color.colorWhite50)
        setListener()
        if (arguments?.getString(Constants.IntentKeys.WALLET_ADDRESS) != null) {
            et_wallet_address?.setText(arguments?.getString(Constants.IntentKeys.WALLET_ADDRESS))
        }

        tv_btc_balance?.text = SharedPrefUtils.readString(Constants.IntentKeys.BTC_BALANCE)
        tv_secondary_balance?.text = SharedPrefUtils.readString(Constants.IntentKeys.USD_BALANCE)


    }

    private fun setListener() {
        btn_scan.setOnClickListener {
            if (PermissionUtils.requestPermission(this,
                            PermissionUtils.REQUEST_CODE_PERMISSION_LOCATION_AND_STORAGE,
                            Manifest.permission.CAMERA)) {
                goToScanner()
            }
        }

        btn_send?.setOnClickListener {
            if (et_wallet_address.text.toString().isNotEmpty()) {
                if (et_amount?.text.toString().isNotEmpty()) {
                    try {
                        presenter.withdrawBalance(
                                arguments?.getInt(Constants.IntentKeys.WALLET_ID)!!,
                                et_wallet_address?.text.toString(),
                                et_amount?.text.toString().toDouble()
                        )
                    } catch (ex: NumberFormatException) {
                        ToastUtils.info(getString(R.string.error_invalid_amount))
                    }
                } else {
                    ToastUtils.info(getString(R.string.error_empty_amount))
                }
            } else {
                ToastUtils.info(getString(R.string.error_empty_wallet))
            }
        }
    }

    private fun goToScanner() {
        val bundle = Bundle()
        bundle.putString(Constants.IntentKeys.USD_BALANCE, tv_secondary_balance?.text.toString())
        bundle.putString(Constants.IntentKeys.BTC_BALANCE, tv_btc_balance?.text.toString())
        (activity as ContainerActivity).visitScanner(bundle)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var isGranted = true
        if (requestCode == PermissionUtils.REQUEST_CODE_PERMISSION_LOCATION_AND_STORAGE) {
            for (i in 0 until permissions.size) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    isGranted = false
                }
            }
            if (isGranted) {
                goToScanner()
            } else {
                ToastUtils.warning(getString(R.string.reach_us_error_loading))
            }
        }
    }


    override fun stopUI() {

    }

    override fun onWithDrawSuccess(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.success(message)
    }

    override fun onWithDrawError(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.error(message)
    }

}