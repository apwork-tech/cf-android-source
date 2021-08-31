package com.wallet.cryptofuelx.main.ui.app.authentication.forgotpassword

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import androidx.core.view.ViewCompat
import com.google.android.material.appbar.AppBarLayout
import com.wallet.cryptofuelx.R
import com.wallet.cryptofuelx.databinding.ActivityForgotPasswordBinding
import com.wallet.cryptofuelx.main.ui.app.authentication.resetpassword.ResetPasswordActivity
import com.wallet.cryptofuelx.main.ui.base.component.BaseActivity
import com.wallet.cryptofuelx.main.ui.base.helper.ProgressDialogUtils
import com.wallet.cryptofuelx.main.ui.base.setRipple
import com.wallet.cryptofuelx.utils.helper.KeyboardUtils
import com.wallet.cryptofuelx.utils.helper.ViewUtils
import com.wallet.cryptofuelx.utils.libs.ToastUtils

class ForgotPasswordActivity : BaseActivity<ForgotPasswordMvpView, ForgotPasswordPresenter>(),
        ForgotPasswordMvpView {

    companion object {
        /**
         * This method starts current activity
         *
         * @param context UI context
         * */
        fun startActivity(context: Context) {
            runCurrentActivity(context, Intent(context, ForgotPasswordActivity::class.java))
        }
    }

    private lateinit var mBinding: ActivityForgotPasswordBinding

    override val layoutResourceId: Int
        get() = R.layout.activity_forgot_password

    override fun getActivityPresenter(): ForgotPasswordPresenter {
        return ForgotPasswordPresenter()
    }

    override fun startUI() {
        mBinding = viewDataBinding as ActivityForgotPasswordBinding
        initialize()
        setListeners()
    }

    private fun initialize() {
        mBinding.textViewRequest.setRipple(R.color.colorWhite50)
        mBinding.imageViewNavigator.setRipple(R.color.colorPrimary50)
    }

    private fun setListeners() {
        setClickListener(mBinding.imageViewNavigator, mBinding.textViewRequest)

        mBinding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            val collapsingToolbar = mBinding.collapsingToolbarLayout
            if (collapsingToolbar.height + verticalOffset < 2 * ViewCompat.getMinimumHeight(collapsingToolbar)) {
                ViewUtils.setLightStatusBar(this@ForgotPasswordActivity)
            } else {
                ViewUtils.clearLightStatusBar(this@ForgotPasswordActivity)
            }
        })
    }

    override fun onClick(view: View) {
        super.onClick(view)

        when (view.id) {
            R.id.image_view_navigator -> {
                finish()
            }

            R.id.text_view_request -> {
                goForResettingPassword()
            }
        }
    }

    private fun goForResettingPassword() {
        if (TextUtils.isEmpty(mBinding.editTextEmail.text.toString().trim())) {
            showAlert(getString(R.string.forgot_password_error_valid_field))
        } else if (!(Patterns.EMAIL_ADDRESS.matcher(mBinding.editTextEmail.text.toString().trim()).matches())) {
            showAlert(getString(R.string.forgot_password_error_valid_email))
        } else {
            KeyboardUtils.hideKeyboard(this)
            presenter.resetPassword(this, mBinding.editTextEmail.text.toString().trim())
        }
    }

    private fun showAlert(message: String) {
        ToastUtils.error(message, true)
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }

    override fun stopUI() {

    }

    override fun onSuccess(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.success(message)
        ResetPasswordActivity.startActivity(this, mBinding.editTextEmail.text.toString())
    }

    override fun onError(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.error(message)
    }
}