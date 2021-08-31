package com.wallet.cryptofuelx.main.ui.app.setting

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import com.wallet.cryptofuelx.R
import com.wallet.cryptofuelx.main.ui.app.change_password.ChangePasswordActivity
import com.wallet.cryptofuelx.main.ui.base.component.BaseActivity
import com.wallet.cryptofuelx.main.ui.base.helper.AlertDialogUtils
import com.wallet.cryptofuelx.utils.helper.DataUtils
import com.wallet.cryptofuelx.utils.libs.ToastUtils
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : BaseActivity<SettingView, SettingPresenter>() {

    companion object {
        /**
         * This method starts current activity
         * @param context UI context
         * */
        fun startActivity(context: Context) {
            val intent = Intent(context, SettingActivity::class.java)
            runCurrentActivity(context, intent)
        }
    }


    override val layoutResourceId: Int
        get() = R.layout.activity_setting

    override fun getActivityPresenter(): SettingPresenter {
        return SettingPresenter()
    }

    private fun setListener() {
        tv_currency_value.setOnClickListener {
            val x = arrayOf("USD", "EURO", "PESO", "TAKA", "INR")
            AlertDialogUtils.on().showDialogList(
                    x,
                    this,
                    getString(R.string.choose_currency),
                    DialogInterface.OnClickListener { dialog, which ->
                        tv_currency_value.text = x[which]
                    }
            )

        }



        btn_back?.setOnClickListener {
            finish()
        }

        tv_language_value.setOnClickListener {
            val y = arrayOf("English", "Spanish", "German", "Hindi", "Bangla")
            AlertDialogUtils.on().showDialogList(
                    y,
                    this,
                    getString(R.string.choose_language),
                    DialogInterface.OnClickListener { dialog, which ->
                        tv_language_value.text = y[which]
                    }
            )
        }

        layout_change_password.setOnClickListener {
            ChangePasswordActivity.startActivity(this)
        }

        tv_help_support?.setOnClickListener {
            ToastUtils.info("Join cryptofuelx forum")
            DataUtils.openLink(this, "https://forum.cryptofuelx.com/index.php")
        }
    }

    override fun startUI() {
        setListener()
    }

    override fun stopUI() {

    }

}