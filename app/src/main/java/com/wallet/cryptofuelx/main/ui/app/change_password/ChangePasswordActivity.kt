package com.wallet.cryptofuelx.main.ui.app.change_password

import android.content.Context
import android.content.Intent
import com.wallet.cryptofuelx.R
import com.wallet.cryptofuelx.main.ui.app.landing.container.ContainerActivity
import com.wallet.cryptofuelx.main.ui.base.component.BaseActivity
import com.wallet.cryptofuelx.main.ui.base.helper.ProgressDialogUtils
import com.wallet.cryptofuelx.main.ui.base.setRipple
import com.wallet.cryptofuelx.utils.libs.ToastUtils
import com.validator.easychecker.EasyChecker
import com.validator.easychecker.exceptions.DeveloperErrorException
import com.validator.easychecker.exceptions.InputErrorException
import com.validator.easychecker.util.PasswordPattern
import kotlinx.android.synthetic.main.activity_change_password.*

class ChangePasswordActivity : BaseActivity<ChangePasswordView, ChangePasswordPresenter>(), ChangePasswordView {

    companion object {
        /**
         * This method starts current activity
         * @param context UI context
         * */
        fun startActivity(context: Context) {
            val intent = Intent(context, ChangePasswordActivity::class.java)
            runCurrentActivity(context, intent)
        }
    }


    override val layoutResourceId: Int
        get() = R.layout.activity_change_password

    override fun getActivityPresenter(): ChangePasswordPresenter {
        return ChangePasswordPresenter()
    }


    override fun startUI() {
        btn_back?.setOnClickListener {
            finish()
        }
        btn_change_password.setOnClickListener {
            try {
                if (EasyChecker.validateInput(
                                this,
                                8,
                                PasswordPattern.PASSWORD_PATTERN_ONE,
                                edit_text_current_password,
                                edit_text_new_password,
                                edit_text_confirm_password
                        )) {
                    presenter.changePassword(
                            edit_text_current_password?.text.toString(),
                            edit_text_new_password?.text.toString(),
                            edit_text_confirm_password?.text.toString()
                    )
                }
            } catch (ex: InputErrorException) {
                ToastUtils.info(ex.messageText)
            } catch (ex: DeveloperErrorException) {
                ex.printStackTrace()
            }


        }

        btn_change_password.setRipple(R.color.colorWhite50)
    }

    override fun stopUI() {

    }

    override fun onChangeSuccess(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.success(message)
        ContainerActivity.startActivity(this)
    }

    override fun onChangeError(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.error(message)
    }


}