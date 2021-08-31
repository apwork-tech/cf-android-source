package com.wallet.cryptofuelx.main.ui.app.landing.dashboard

import com.wallet.cryptofuelx.main.data.remote.response.home_data.HomeData
import com.wallet.cryptofuelx.main.ui.base.callback.MvpView

interface DashboardMvpView : MvpView {
    fun onHomeDataSuccess(homeData: HomeData, type:Int)
    fun onHomeDataError(message: String)
}