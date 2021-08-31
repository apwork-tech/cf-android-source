package com.wallet.cryptofuelx.main.ui.app.authentication.registration

import android.content.Context
import android.content.Intent
import android.text.InputType
import android.view.View
import androidx.core.view.ViewCompat
import com.google.android.material.appbar.AppBarLayout
import com.wallet.cryptofuelx.R
import com.wallet.cryptofuelx.databinding.ActivityRegistrationBinding
import com.wallet.cryptofuelx.main.ui.app.authentication.login.LoginActivity
import com.wallet.cryptofuelx.main.ui.app.authentication.verification.CodeVerificationActivity
import com.wallet.cryptofuelx.main.ui.base.component.BaseActivity
import com.wallet.cryptofuelx.main.ui.base.helper.ProgressDialogUtils
import com.wallet.cryptofuelx.main.ui.base.setRipple
import com.wallet.cryptofuelx.utils.helper.KeyboardUtils
import com.wallet.cryptofuelx.utils.helper.ViewUtils
import com.wallet.cryptofuelx.utils.libs.ToastUtils
import com.validator.easychecker.EasyChecker
import com.validator.easychecker.exceptions.InputErrorException
import com.validator.easychecker.util.PasswordPattern
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : BaseActivity<RegistrationMvpView, RegistrationPresenter>(),
        RegistrationMvpView {

    companion object {
        /**
         * This method starts current activity
         *
         * @param context UI context
         * */
        fun startActivity(context: Context) {
            runCurrentActivity(context, Intent(context,
                    RegistrationActivity::class.java))
        }
    }

    private lateinit var mBinding: ActivityRegistrationBinding

    override val layoutResourceId: Int
        get() = R.layout.activity_registration

    override fun getActivityPresenter(): RegistrationPresenter {
        return RegistrationPresenter()
    }

    override fun startUI() {
        mBinding = viewDataBinding as ActivityRegistrationBinding
        initialize()
        setListeners()
    }

    private fun initialize() {
        val desiredInputType = (InputType.TYPE_CLASS_TEXT or
                InputType.TYPE_TEXT_VARIATION_PASSWORD)
        mBinding.editTextPassword.inputType = desiredInputType
        mBinding.editTextPassword.typeface = mBinding.editTextEmail.typeface
        mBinding.editTextConfirmPassword.inputType = desiredInputType
        mBinding.editTextConfirmPassword.typeface = mBinding.editTextEmail.typeface
        mBinding.textViewRegister.setRipple(R.color.colorWhite50)
        mBinding.imageViewNavigator.setRipple(R.color.colorPrimary50)
        mBinding.constraintLayoutHaveAccountContainer.setRipple(R.color.colorPrimary50)
    }

    private fun setListeners() {
        setClickListener(mBinding.constraintLayoutHaveAccountContainer,
                mBinding.textViewRegister,
                mBinding.imageViewNavigator)
        mBinding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            val collapsingToolbar = mBinding.collapsingToolbarLayout
            if (collapsingToolbar.height + verticalOffset < 2 * ViewCompat.getMinimumHeight(collapsingToolbar)) {
                ViewUtils.setLightStatusBar(this@RegistrationActivity)
            } else {
                ViewUtils.clearLightStatusBar(this@RegistrationActivity)
            }
        })
    }

    override fun onClick(view: View) {
        super.onClick(view)

        when (view.id) {
            R.id.constraint_layout_have_account_container -> {
                LoginActivity.startActivity(this)
            }

            R.id.image_view_navigator -> {
                onBackPressed()
            }

            R.id.text_view_register -> {
                goForRegistration()
            }
        }
    }

    private fun goForRegistration() {
        KeyboardUtils.hideKeyboard(this)
        try {
            EasyChecker.validateInput(
                    this,
                    8,
                    PasswordPattern.PASSWORD_PATTERN_ONE,
                    edit_text_first_name,
                    edit_text_last_name,
                    edit_text_email,
                    edit_text_mobile_phone,
                    edit_text_password,
                    edit_text_confirm_password
            )
            presenter.registerTheUser(
                    this,
                    edit_text_first_name.text.toString(),
                    edit_text_last_name.text.toString(),
                    edit_text_email.text.toString(),
                    edit_text_mobile_phone.text.toString(),
                    edit_text_password.text.toString(),
                    edit_text_confirm_password.text.toString()
            )
        } catch (inputException: InputErrorException) {
            ToastUtils.error(inputException.messageText,
                    true)
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
        ToastUtils.success(message, true)
        CodeVerificationActivity.startActivity(this, edit_text_email?.text.toString())
    }

    override fun onError(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.error(message)
    }
}