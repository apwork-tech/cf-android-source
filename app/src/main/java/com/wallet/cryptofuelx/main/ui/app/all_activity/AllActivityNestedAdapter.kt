package com.wallet.cryptofuelx.main.ui.app.all_activity

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.wallet.cryptofuelx.R
import com.wallet.cryptofuelx.databinding.ItemAllActivityNestedBinding
import com.wallet.cryptofuelx.main.ui.app.all_activity.demo_model.Transaction
import com.wallet.cryptofuelx.main.ui.base.component.BaseAdapter
import com.wallet.cryptofuelx.main.ui.base.component.BaseViewHolder

class AllActivityNestedAdapter : BaseAdapter<Transaction>() {

    override fun isEqual(left: Transaction, right: Transaction): Boolean {
        return left == right
    }

    override fun newViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Transaction> {
        return AllActivityNestedViewHolder(
                inflate(parent,
                        R.layout.item_all_activity_nested
                )
        )
    }

    inner class AllActivityNestedViewHolder(binding: ViewDataBinding) : BaseViewHolder<Transaction>(binding) {
        private var mBinding = binding as ItemAllActivityNestedBinding
        override fun bind(item: Transaction) {
            mBinding.icIconAllActivity.setImageResource(item.icon)
            mBinding.tvTransactionAmount.text = item.transactionAmount
            mBinding.tvWalletName.text = item.walletName
            mBinding.tvWalletMessage.text = item.transactionMessage
        }
    }
}