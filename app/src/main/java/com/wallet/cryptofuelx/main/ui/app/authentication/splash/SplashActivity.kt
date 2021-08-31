package com.wallet.cryptofuelx.main.ui.app.authentication.splash

import android.os.Handler
import com.wallet.cryptofuelx.main.ui.app.authentication.login.LoginActivity
import com.wallet.cryptofuelx.main.ui.app.landing.container.ContainerActivity
import com.wallet.cryptofuelx.main.ui.base.component.BaseActivity
import com.wallet.cryptofuelx.utils.helper.Constants
import com.wallet.cryptofuelx.utils.helper.SharedPrefUtils

class SplashActivity : BaseActivity<SplashMvpView, SplashPresenter>(), SplashMvpView {
    override val layoutResourceId: Int
        get() = INVALID_ID

    override fun getActivityPresenter(): SplashPresenter {
        return SplashPresenter()
    }

    override fun startUI() {
        Handler().postDelayed({
            if (SharedPrefUtils.readBoolean(Constants.PreferenceKeys.LOGGED_IN)) {
                ContainerActivity.startActivity(this)
                finish()
            } else {
                LoginActivity.startActivity(this)
                finish()
            }
        }, 2000)
    }

    override fun stopUI() {

    }
}