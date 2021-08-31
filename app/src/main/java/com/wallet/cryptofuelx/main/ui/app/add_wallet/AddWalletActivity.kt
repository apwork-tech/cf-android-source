package com.wallet.cryptofuelx.main.ui.app.add_wallet

import android.content.Context
import android.content.Intent
import com.wallet.cryptofuelx.R
import com.wallet.cryptofuelx.main.ui.app.setting.SettingActivity
import com.wallet.cryptofuelx.main.ui.base.component.BaseActivity
import com.wallet.cryptofuelx.main.ui.base.helper.ProgressDialogUtils
import com.wallet.cryptofuelx.utils.libs.ToastUtils
import kotlinx.android.synthetic.main.activity_add_wallet.*


class AddWalletActivity : BaseActivity<AddWalletView, AddWalletPresenter>(), AddWalletView {

    companion object {
        /**
         * This method starts current activity
         * @param context UI context
         * */
        fun startActivity(context: Context) {
            val intent = Intent(context, AddWalletActivity::class.java)
            runCurrentActivity(context, intent)
        }
    }

    override val layoutResourceId: Int
        get() = R.layout.activity_add_wallet

    override fun getActivityPresenter(): AddWalletPresenter {
        return AddWalletPresenter()
    }

    override fun startUI() {
        setListener()
    }

    override fun stopUI() {

    }

    private fun setListener() {
        btn_add_wallet?.setOnClickListener {
            if (edit_text_wallet_name?.text.toString().isNotEmpty()) {
                presenter.createWallet(edit_text_wallet_name.text.toString())
            } else {
                ToastUtils.info(getString(R.string.empty_wallet_name))
            }
        }

        btn_back?.setOnClickListener {
            finish()
        }

        image_view_settings?.setOnClickListener {
            SettingActivity.startActivity(this)
        }
    }

    override fun onWalletCreateSuccess(status: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.success(status)
        finish()
    }

    override fun onWalletCreateError(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.error(message)
    }

}
