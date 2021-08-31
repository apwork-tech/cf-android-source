package com.wallet.cryptofuelx.main.ui.app.landing.my_wallet

import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.wallet.cryptofuelx.R
import com.wallet.cryptofuelx.databinding.ItemMyWalletBinding
import com.wallet.cryptofuelx.main.data.remote.response.my_wallet.Wallet
import com.wallet.cryptofuelx.main.ui.base.component.BaseAdapter
import com.wallet.cryptofuelx.main.ui.base.component.BaseViewHolder

class MyWalletAdapter : BaseAdapter<Wallet>() {

    override fun isEqual(left: Wallet, right: Wallet): Boolean {
        return left == right
    }

    override fun newViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Wallet> {
        return MyWalletListHolder(
                inflate(parent, R.layout.item_my_wallet)
        )
    }

    inner class MyWalletListHolder(binding: ViewDataBinding) : BaseViewHolder<Wallet>(binding) {
        private var mBinding = binding as ItemMyWalletBinding
        override fun bind(item: Wallet) {
            //implementation of data with UI views
            setClickListener(mBinding.btnDeposit)
            setClickListener(mBinding.btnWithdraw)
            setClickListener(mBinding.btnActivity)
            mBinding.tvWalletName.text = item.name
            mBinding.tvWalletBalance.text = item.balance
        }

        override fun onClick(view: View) {
            mItemClickListener?.onItemClick(view, getItem(adapterPosition)!!)
        }
    }

}