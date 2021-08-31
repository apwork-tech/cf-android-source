package com.wallet.cryptofuelx.main.ui.app.referral

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.wallet.cryptofuelx.R
import com.wallet.cryptofuelx.databinding.ItemReferralBinding
import com.wallet.cryptofuelx.main.data.remote.response.my_referral.MyReferralX
import com.wallet.cryptofuelx.main.ui.base.component.BaseAdapter
import com.wallet.cryptofuelx.main.ui.base.component.BaseViewHolder


class ReferralAdapter : BaseAdapter<MyReferralX>() {

    override fun isEqual(left: MyReferralX, right: MyReferralX): Boolean {
        return left == right
    }

    override fun newViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<MyReferralX> {
        return ReferralViewHolder(
                inflate(
                        parent,
                        R.layout.item_referral
                )
        )
    }

    inner class ReferralViewHolder(binding: ViewDataBinding) : BaseViewHolder<MyReferralX>(binding) {
        private val mBinding = binding as ItemReferralBinding
        override fun bind(item: MyReferralX) {
            mBinding.tvReferralUserName.text = item.fullName
            mBinding.tvReferralLocation.text = item.level
            mBinding.tvReferralJoinDate.text = item.joiningDate.split(" ")[0]
        }
    }

}