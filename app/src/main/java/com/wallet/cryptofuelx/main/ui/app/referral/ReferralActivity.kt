package com.wallet.cryptofuelx.main.ui.app.referral

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.wallet.cryptofuelx.R
import com.wallet.cryptofuelx.main.data.remote.response.my_referral.MyReferral
import com.wallet.cryptofuelx.main.ui.app.setting.SettingActivity
import com.wallet.cryptofuelx.main.ui.base.component.BaseActivity
import com.wallet.cryptofuelx.main.ui.base.helper.ProgressDialogUtils
import com.wallet.cryptofuelx.utils.helper.DataUtils
import com.wallet.cryptofuelx.utils.helper.ViewUtils
import com.wallet.cryptofuelx.utils.libs.ToastUtils
import kotlinx.android.synthetic.main.activity_referral.*


class ReferralActivity : BaseActivity<ReferralView, ReferralPresenter>(), ReferralView {

    companion object {
        /**
         * This method starts current activity
         * @param context UI context
         * */
        fun startActivity(context: Context) {
            val intent = Intent(context, ReferralActivity::class.java)
            runCurrentActivity(context, intent)
        }
    }

    override val layoutResourceId: Int
        get() = R.layout.activity_referral

    override fun getActivityPresenter(): ReferralPresenter {
        return ReferralPresenter()
    }

    override fun startUI() {
        initializeReferralList()
        presenter.getMyReferral()
        setListener()
    }

    private fun initializeReferralList() {
        ViewUtils.initializeRecyclerView(
                recycler_view_referral,
                ReferralAdapter(),
                null,
                null,
                LinearLayoutManager(this),
                null,
                null,
                null
        )
    }

    override fun stopUI() {

    }

    private fun setListener() {
        tv_copy?.setOnClickListener {
            DataUtils.copyTextToClipBoard(this, tv_referral_address?.text.toString())
            ToastUtils.success(getString(R.string.referral_url_copied))
        }
        btn_back?.setOnClickListener {
            finish()
        }
        image_view_settings?.setOnClickListener {
            SettingActivity.startActivity(this)
        }

        img_facebook?.setOnClickListener {
            DataUtils.shareToFacebook(this, String.format("Open a account with my referral link to receive bonus, Link: %s",
                    tv_referral_address?.text.toString()))
        }

        img_twitter?.setOnClickListener {
            DataUtils.shareToTwitter(this, String.format("Open a account with my referral link to receive bonus, Link: %s",
                    tv_referral_address?.text.toString()))
        }
    }

    private fun getReferralAdapter(): ReferralAdapter {
        return recycler_view_referral?.adapter as ReferralAdapter
    }


    override fun onReferralGetSucces(myReferral: MyReferral) {
        ProgressDialogUtils.on().hideProgressDialog()
        getReferralAdapter().addItems(myReferral.myReferralList)
        tv_referral_address?.text = myReferral.url
    }

    override fun onReferralGetError(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.error(message)
    }


}