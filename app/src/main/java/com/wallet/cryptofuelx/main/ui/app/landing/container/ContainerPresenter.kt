package com.wallet.cryptofuelx.main.ui.app.landing.container

import com.wallet.cryptofuelx.R
import com.wallet.cryptofuelx.main.data.local.model.NavigationDrawerItem
import com.wallet.cryptofuelx.main.ui.base.component.BasePresenter
import com.wallet.cryptofuelx.utils.helper.DataUtils

class ContainerPresenter : BasePresenter<ContainerMvpView>() {
    fun getDrawerMenuList(): List<NavigationDrawerItem> {
        val list: MutableList<NavigationDrawerItem> = ArrayList()
        list.add(NavigationDrawerItem(DataUtils.getString(R.string.menu_add_wallet),
                R.drawable.add_wallet))
        list.add(NavigationDrawerItem(DataUtils.getString(R.string.menu_profile),
                R.drawable.ic_profile))
        list.add(NavigationDrawerItem(DataUtils.getString(R.string.menu_referral),
                R.drawable.ic_referral))
        list.add(NavigationDrawerItem(DataUtils.getString(R.string.dashboard_drawer_log_out),
                R.drawable.ic_logout))
        return list
    }
}