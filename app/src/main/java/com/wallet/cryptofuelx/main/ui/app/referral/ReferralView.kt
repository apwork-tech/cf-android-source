package com.wallet.cryptofuelx.main.ui.app.referral

import com.wallet.cryptofuelx.main.data.remote.response.my_referral.MyReferral
import com.wallet.cryptofuelx.main.ui.base.callback.MvpView

interface ReferralView : MvpView {
    fun onReferralGetSucces(myReferral: MyReferral)
    fun onReferralGetError(message: String)
}