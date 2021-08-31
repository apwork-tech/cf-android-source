package com.wallet.cryptofuelx.main.ui.app.authentication.login

import android.content.Context
import android.content.Intent
import android.text.InputType
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import androidx.core.view.ViewCompat
import com.google.android.material.appbar.AppBarLayout
import com.wallet.cryptofuelx.R
import com.wallet.cryptofuelx.databinding.ActivityLoginBinding
import com.wallet.cryptofuelx.main.ui.app.authentication.forgotpassword.ForgotPasswordActivity
import com.wallet.cryptofuelx.main.ui.app.authentication.registration.RegistrationActivity
import com.wallet.cryptofuelx.main.ui.app.landing.container.ContainerActivity
import com.wallet.cryptofuelx.main.ui.base.component.BaseActivity
import com.wallet.cryptofuelx.main.ui.base.helper.ProgressDialogUtils
import com.wallet.cryptofuelx.main.ui.base.setRipple
import com.wallet.cryptofuelx.utils.helper.DataUtils
import com.wallet.cryptofuelx.utils.helper.KeyboardUtils
import com.wallet.cryptofuelx.utils.helper.ViewUtils
import com.wallet.cryptofuelx.utils.libs.ToastUtils


class LoginActivity : BaseActivity<LoginMvpView, LoginPresenter>(), LoginMvpView {

    companion object {
        /**
         * This method starts current activity
         *
         * @param context UI context
         * */
        fun startActivity(context: Context) {
            runCurrentActivity(context, Intent(context, LoginActivity::class.java))
        }
    }

    private lateinit var mBinding: ActivityLoginBinding

    override val layoutResourceId: Int
        get() = R.layout.activity_login

    override fun getActivityPresenter(): LoginPresenter {
        return LoginPresenter()
    }

    override fun startUI() {
        mBinding = viewDataBinding as ActivityLoginBinding
        initialize()
        setListeners()
    }

    private fun initialize() {
        val desiredInputType = (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
        mBinding.editTextPassword.inputType = desiredInputType
        mBinding.editTextPassword.typeface = mBinding.editTextEmail.typeface
        mBinding.textViewLogin.setRipple(R.color.colorWhite50)
        mBinding.imageViewNavigator.setRipple(R.color.colorPrimary50)
        mBinding.constraintLayoutHaveAccountContainer.setRipple(R.color.colorPrimary50)
        mBinding.textViewForgotPassword.setRipple(R.color.colorPrimary50)
    }

    private fun setListeners() {
        setClickListener(mBinding.textViewForgotPassword,
                mBinding.textViewLogin,
                mBinding.imageViewNavigator,
                mBinding.constraintLayoutHaveAccountContainer)
        mBinding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            val collapsingToolbar = mBinding.collapsingToolbarLayout
            if (collapsingToolbar.height + verticalOffset < 2 * ViewCompat.getMinimumHeight(collapsingToolbar)) {
                ViewUtils.setLightStatusBar(this@LoginActivity)
            } else {
                ViewUtils.clearLightStatusBar(this@LoginActivity)
            }
        })
    }

    override fun onClick(view: View) {
        super.onClick(view)

        when (view.id) {
            R.id.text_view_forgot_password -> {
                ForgotPasswordActivity.startActivity(this)
            }

            R.id.image_view_navigator -> {
                onBackPressed()
            }

            R.id.constraint_layout_have_account_container -> {
                RegistrationActivity.startActivity(this)
            }

            R.id.text_view_login -> {
                goForLogin()
            }
        }
    }

    private fun goForLogin() {
        if (TextUtils.isEmpty(mBinding.editTextEmail.text.toString().trim())
                || TextUtils.isEmpty(mBinding.editTextPassword.text.toString().trim())) {
            showAlert(getString(R.string.login_error_valid_fields))
        } else {
            if (!(Patterns.EMAIL_ADDRESS.matcher(mBinding.editTextEmail.text.toString().trim()).matches())) {
                showAlert(getString(R.string.login_error_valid_email))
            } else if (mBinding.editTextPassword.text.toString().trim().length <
                    DataUtils.getInteger(R.integer.character_min_limit_password)
                    && mBinding.editTextPassword.text.toString().trim().length >
                    DataUtils.getInteger(R.integer.character_max_limit_password)) {
                showAlert(getString(R.string.login_error_valid_password))
            } else {
                KeyboardUtils.hideKeyboard(this)
                presenter.login(this, mBinding.editTextEmail.text.toString().trim(),
                        mBinding.editTextPassword.text.toString().trim())
            }
        }
    }

    private fun showAlert(message: String) {
        ToastUtils.error(message, true)
    }

    override fun stopUI() {

    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }

    override fun onSuccess(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ContainerActivity.startActivity(this)
        finish()
    }

    override fun onError(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.error(message)
    }
}