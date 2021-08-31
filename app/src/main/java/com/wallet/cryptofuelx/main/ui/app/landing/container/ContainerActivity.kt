package com.wallet.cryptofuelx.main.ui.app.landing.container

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wallet.cryptofuelx.R
import com.wallet.cryptofuelx.main.data.BaseRepository
import com.wallet.cryptofuelx.main.data.local.model.NavigationDrawerItem
import com.wallet.cryptofuelx.main.ui.app.add_wallet.AddWalletActivity
import com.wallet.cryptofuelx.main.ui.app.all_activity.AllTransactionActivity

import com.wallet.cryptofuelx.main.ui.app.landing.buy_coin.BuyCoinFragment
import com.wallet.cryptofuelx.main.ui.app.landing.my_wallet.MyWalletFragment
import com.wallet.cryptofuelx.main.ui.app.landing.dashboard.DashboardFragment
import com.wallet.cryptofuelx.main.ui.app.landing.deposit.DepositFragment
import com.wallet.cryptofuelx.main.ui.app.landing.home.DrawerAdapter
import com.wallet.cryptofuelx.main.ui.app.landing.scanner.ScannerFragment
import com.wallet.cryptofuelx.main.ui.app.landing.wallet_transaction.WalletTransactionFragment
import com.wallet.cryptofuelx.main.ui.app.landing.withdraw.WithdrawFragment
import com.wallet.cryptofuelx.main.ui.app.move_coin.MoveCoinActivity
import com.wallet.cryptofuelx.main.ui.app.referral.ReferralActivity

import com.wallet.cryptofuelx.main.ui.app.setting.SettingActivity
import com.wallet.cryptofuelx.main.ui.app.userprofile.UserProfileActivity
import com.wallet.cryptofuelx.main.ui.base.callback.ItemClickListener
import com.wallet.cryptofuelx.main.ui.base.component.BaseActivity
import com.wallet.cryptofuelx.main.ui.base.helper.AlertDialogUtils
import com.wallet.cryptofuelx.main.ui.base.helper.LinearMarginItemDecoration
import com.wallet.cryptofuelx.utils.helper.Constants
import com.wallet.cryptofuelx.utils.helper.PermissionUtils
import com.wallet.cryptofuelx.utils.helper.SharedPrefUtils
import com.wallet.cryptofuelx.utils.helper.ViewUtils
import com.wallet.cryptofuelx.utils.libs.GlideUtils
import com.wallet.cryptofuelx.utils.libs.ToastUtils
import kotlinx.android.synthetic.main.activity_container.*
import kotlinx.android.synthetic.main.navigation_drawer.*


class ContainerActivity : BaseActivity<ContainerMvpView, ContainerPresenter>() {

    companion object {
        /**
         * This method starts current activity
         * @param context UI context
         * */
        fun startActivity(context: Context) {
            val intent = Intent(context, ContainerActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            runCurrentActivity(context, intent)
        }
    }

    private lateinit var mBinding: com.wallet.cryptofuelx.databinding.ActivityContainerBinding

    override val layoutResourceId: Int
        get() = R.layout.activity_container

    override fun getActivityPresenter(): ContainerPresenter {
        return ContainerPresenter()
    }

    override fun startUI() {
        mBinding = viewDataBinding as com.wallet.cryptofuelx.databinding.ActivityContainerBinding
        setListeners()
        visitHome()
        initializeSideMenu()
    }

    override fun stopUI() {

    }

    override fun onBackPressed() {
        if (currentFragment is DepositFragment
                ||
                currentFragment is WithdrawFragment
                ||
                currentFragment is WalletTransactionFragment
        ) {
            visitWallet()
        } else {
            super.onBackPressed()
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.image_view_drawer_closer -> {
                mBinding.drawerLayoutWholeContainer.closeDrawers()
            }

            R.id.image_view_drawer_opener -> {
                mBinding.drawerLayoutWholeContainer.openDrawer(GravityCompat.START)
            }

        }
    }

    private fun setListeners() {
        mBinding.bottomNavigationBar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_dashboard -> {
                    it.isCheckable = true
                    visitHome()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.nav_wallet -> {
                    it.isCheckable = true
                    visitWallet()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.nav_buy_coin -> {
                    it.isCheckable = true
                    visitBuyCoin()
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }

        image_view_settings.setOnClickListener {
            visitSetting()
        }

        image_view_drawer_opener?.setOnClickListener {
            mBinding.drawerLayoutWholeContainer.openDrawer(GravityCompat.START)
        }

        text_view_user_name?.text = SharedPrefUtils.readString(Constants.PreferenceKeys.NAME)
        GlideUtils.normalWithCaching(image_view_user, SharedPrefUtils.readString(Constants.PreferenceKeys.IMAGE))
    }

    private fun initializeSideMenu() {
        ViewUtils.initializeRecyclerView(getDrawerRecyclerView(), DrawerAdapter(),
                object : ItemClickListener<NavigationDrawerItem> {
                    override fun onItemClick(view: View, item: NavigationDrawerItem) {
                        mBinding.drawerLayoutWholeContainer.closeDrawers()
                        when (item.resourceId) {

                            R.drawable.add_wallet -> {
                                visitAddWallet()
                            }

                            R.drawable.ic_profile -> {
                                visitProfile()
                            }

                            R.drawable.ic_referral -> {
                                visitReferral()
                            }

                            R.drawable.ic_logout -> {
                                AlertDialogUtils.on().showNativeDialog(this@ContainerActivity,
                                        true,
                                        getString(R.string.my_appointments_yes),
                                        DialogInterface.OnClickListener { dialog, _ ->
                                            dialog.dismiss()
                                            presenter.compositeDisposable.add(
                                                    BaseRepository.on().logOut(this@ContainerActivity,
                                                            true))
                                        },
                                        getString(R.string.my_appointments_no),
                                        DialogInterface.OnClickListener { dialog, _ ->
                                            dialog.dismiss()
                                        },
                                        getString(R.string.my_appointments_are_you_sure),
                                        null,
                                        null)
                            }
                        }
                    }
                },
                null,
                LinearLayoutManager(this),
                LinearMarginItemDecoration(ViewUtils.getPixel(R.dimen.margin_32),
                        ViewUtils.getPixel(R.dimen.margin_32),
                        ViewUtils.getPixel(R.dimen.margin_16),
                        ViewUtils.getPixel(R.dimen.margin_16)),
                null,
                null)

        mBinding.navigationViewContainer.background = null
        getAdapter().clear()
        getAdapter().addItems(presenter.getDrawerMenuList())
    }



    private fun getDrawerRecyclerView(): RecyclerView {
        return mBinding.navigationView.recyclerViewMenu
    }

    private fun getAdapter(): DrawerAdapter {
        return getDrawerRecyclerView().adapter as DrawerAdapter
    }

    fun setPageTitle(title: String) {
        mBinding.textViewTitle.text = title
    }

    fun visitHome() {
        commitFragment(R.id.constraint_layout_full_fragment_container, DashboardFragment())
        text_view_title?.text = "Dashboard"
    }

    fun visitWallet() {
        commitFragment(R.id.constraint_layout_full_fragment_container, MyWalletFragment())
        text_view_title?.text = "My Wallet"
    }

    fun visitAddWallet() {
        AddWalletActivity.startActivity(this)
    }

    fun visitProfile() {
        UserProfileActivity.startActivity(this)
    }

    fun visitScanner(bundle: Bundle) {
        commitFragment(R.id.constraint_layout_full_fragment_container, ScannerFragment())
    }

    fun visitBuyCoin() {
        commitFragment(R.id.constraint_layout_full_fragment_container, BuyCoinFragment())
        text_view_title?.text = "Buy Coin"
    }

    fun visitWalletTransaction(bundle: Bundle) {
        val walletTransactionFragment = WalletTransactionFragment()
        walletTransactionFragment.arguments = bundle
        commitFragment(R.id.constraint_layout_full_fragment_container, walletTransactionFragment)
        text_view_title?.text = "Wallet Transaction"
    }

    fun visitWithDraw(bundle: Bundle) {
        val withdrawFragment = WithdrawFragment()
        withdrawFragment.arguments = bundle
        commitFragment(R.id.constraint_layout_full_fragment_container, withdrawFragment)
        text_view_title?.text = "Withdraw Coin"
    }

    fun visitDepositIntoWallet(bundle: Bundle) {
        val depositFragment = DepositFragment()
        depositFragment.arguments = bundle
        commitFragment(R.id.constraint_layout_full_fragment_container, depositFragment)
        text_view_title?.text = "Deposit Coin"
    }

    fun visitAllActivity() {
        AllTransactionActivity.startActivity(this)
    }

    fun visitReferral() {
        ReferralActivity.startActivity(this)
    }

    fun visitMoveCoin() {
        MoveCoinActivity.startActivity(this)
    }

    fun visitSetting() {
        SettingActivity.startActivity(this)
    }

    fun visitReachUs() {
        if (PermissionUtils.requestPermission(this,
                        PermissionUtils.REQUEST_CODE_PERMISSION_LOCATION_AND_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var isGranted = true
        if (requestCode == PermissionUtils.REQUEST_CODE_PERMISSION_LOCATION_AND_STORAGE) {
            for (i in 0 until permissions.size) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    isGranted = false
                }
            }
            if (isGranted) {
                visitReachUs()
            } else {
                ToastUtils.warning(getString(R.string.reach_us_error_loading))
            }
        }
    }


}