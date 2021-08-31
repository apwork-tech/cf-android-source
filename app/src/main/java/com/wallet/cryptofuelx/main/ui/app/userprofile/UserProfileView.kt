package com.wallet.cryptofuelx.main.ui.app.userprofile

import com.wallet.cryptofuelx.main.data.remote.response.profile.ProfileResponse
import com.wallet.cryptofuelx.main.ui.base.callback.MvpView

interface UserProfileView : MvpView {
    fun getProfileDataSuccess(profileResponse: ProfileResponse)
    fun getProfileDataError(message: String)
    fun onProfileUpdateSuccess(profileResponse: ProfileResponse)
    fun onProfileUpdateError(message: String)
}