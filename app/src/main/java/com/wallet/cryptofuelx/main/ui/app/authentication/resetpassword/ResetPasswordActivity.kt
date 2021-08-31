package com.wallet.cryptofuelx.main.ui.app.authentication.resetpassword

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.view.ViewCompat
import com.google.android.material.appbar.AppBarLayout
import com.wallet.cryptofuelx.R
import com.wallet.cryptofuelx.databinding.ActivityResetPasswordBinding
import com.wallet.cryptofuelx.main.ui.app.authentication.login.LoginActivity
import com.wallet.cryptofuelx.main.ui.base.component.BaseActivity
import com.wallet.cryptofuelx.main.ui.base.helper.ProgressDialogUtils
import com.wallet.cryptofuelx.main.ui.base.setRipple
import com.wallet.cryptofuelx.utils.helper.Constants
import com.wallet.cryptofuelx.utils.helper.ViewUtils
import com.wallet.cryptofuelx.utils.libs.ToastUtils
import com.validator.easychecker.EasyChecker
import com.validator.easychecker.exceptions.InputErrorException
import com.validator.easychecker.util.PasswordPattern
import kotlinx.android.synthetic.main.activity_reset_password.*

class ResetPasswordActivity : BaseActivity<ResetPasswordMvpView, ResetPasswordPresenter>(), ResetPasswordMvpView {

    companion object {
        /**
         * This method starts current activity
         *
         * @param context UI context
         * */
        fun startActivity(context: Context, text: String?) {
            runCurrentActivity(context, Intent(context, ResetPasswordActivity::class.java)
                    .putExtra(Constants.IntentKeys.EMAIL, text))
        }
    }

    private lateinit var mBinding: ActivityResetPasswordBinding

    override val layoutResourceId: Int
        get() = R.layout.activity_reset_password

    override fun getActivityPresenter(): ResetPasswordPresenter {
        return ResetPasswordPresenter()
    }

    override fun startUI() {
        mBinding = viewDataBinding as ActivityResetPasswordBinding
        initialize()
        setListeners()
    }

    private fun initialize() {
        mBinding.textViewResetPassword.setRipple(R.color.colorWhite50)
        mBinding.imageViewNavigator.setRipple(R.color.colorPrimary50)
    }

    private fun setListeners() {
        setClickListener(mBinding.imageViewNavigator, mBinding.textViewResetPassword)

        mBinding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            val collapsingToolbar = mBinding.collapsingToolbarLayout
            if (collapsingToolbar.height + verticalOffset < 2 * ViewCompat.getMinimumHeight(collapsingToolbar)) {
                ViewUtils.setLightStatusBar(this@ResetPasswordActivity)
            } else {
                ViewUtils.clearLightStatusBar(this@ResetPasswordActivity)
            }
        })
    }

    override fun stopUI() {

    }

    override fun onClick(view: View) {
        super.onClick(view)

        when (view.id) {
            R.id.image_view_navigator -> {
                onBackPressed()
            }

            R.id.text_view_reset_password -> {
                try {
                    EasyChecker.validateInput(
                            this,
                            8,
                            PasswordPattern.PASSWORD_PATTERN_ONE,
                            edit_text_access_code,
                            edit_text_password,
                            edit_text_confirm_password
                    )
                    presenter.resetPassword(intent?.getStringExtra(Constants.IntentKeys.EMAIL)!!,
                            edit_text_access_code?.text.toString(),
                            edit_text_password?.text.toString(),
                            edit_text_confirm_password?.text.toString()
                    )
                } catch (ex: InputErrorException) {
                    ToastUtils.error(ex.messageText)
                }

            }
        }
    }

    override fun onSuccess(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.success(message)
        LoginActivity.startActivity(this)
    }

    override fun onError(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.error(message)
    }
}