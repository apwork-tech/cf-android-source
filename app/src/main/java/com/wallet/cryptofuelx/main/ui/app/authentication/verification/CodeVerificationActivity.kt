package com.wallet.cryptofuelx.main.ui.app.authentication.verification

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.view.ViewCompat
import com.google.android.material.appbar.AppBarLayout
import com.wallet.cryptofuelx.R
import com.wallet.cryptofuelx.databinding.ActivityCodeVerificationBinding
import com.wallet.cryptofuelx.main.ui.app.landing.container.ContainerActivity
import com.wallet.cryptofuelx.main.ui.base.clearLightStatusBar
import com.wallet.cryptofuelx.main.ui.base.component.BaseActivity
import com.wallet.cryptofuelx.main.ui.base.helper.ProgressDialogUtils
import com.wallet.cryptofuelx.main.ui.base.setLightStatusBar
import com.wallet.cryptofuelx.main.ui.base.setRipple
import com.wallet.cryptofuelx.utils.helper.Constants
import com.wallet.cryptofuelx.utils.libs.ToastUtils
import kotlinx.android.synthetic.main.activity_code_verification.*
import java.lang.StringBuilder

class CodeVerificationActivity : BaseActivity<CodeVerificationMvpView, CodeVerificationPresenter>(),
        CodeVerificationMvpView {
    //global variable
    val accessCodeBuilder = StringBuilder()

    companion object {
        /**
         * This method starts current activity
         * @param context UI context
         * */
        fun startActivity(context: Context, email: String) {
            runCurrentActivity(context, Intent(context, CodeVerificationActivity::class.java)
                    .putExtra(Constants.IntentKeys.EMAIL, email)
            )
        }
    }

    private lateinit var mBinding: ActivityCodeVerificationBinding

    override val layoutResourceId: Int
        get() = R.layout.activity_code_verification

    override fun getActivityPresenter(): CodeVerificationPresenter {
        return CodeVerificationPresenter()
    }

    override fun startUI() {
        mBinding = viewDataBinding as ActivityCodeVerificationBinding
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
                setLightStatusBar()
            } else {
                clearLightStatusBar()
            }
        })

        pin_entry_edit_text_code_verification.setOnPinEnteredListener {
            accessCodeBuilder.append(it)
        }
    }

    override fun stopUI() {

    }

    override fun onClick(view: View) {
        super.onClick(view)

        when (view.id) {
            R.id.image_view_navigator -> {
                onBackPressed()
            }

            R.id.text_view_request -> {
                if (accessCodeBuilder.length < 6) {
                    ToastUtils.error(getString(R.string.error_verification_code))
                } else {
                    presenter.verifyEmail(
                            intent?.getStringExtra(Constants.IntentKeys.EMAIL)!!,
                            accessCodeBuilder.toString()
                    )
                }
            }
        }
    }

    override fun onSuccess(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.success(message)
        ContainerActivity.startActivity(this)
        finish()
    }

    override fun onError(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.error(message)
    }
}