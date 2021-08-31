package com.wallet.cryptofuelx.main.ui.app.landing.wallet_transaction

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding

import com.wallet.cryptofuelx.R
import com.wallet.cryptofuelx.databinding.ItemWalletTransactionBinding
import com.wallet.cryptofuelx.main.data.remote.response.wallet_transaction.Transaction
import com.wallet.cryptofuelx.main.ui.base.component.BaseAdapter
import com.wallet.cryptofuelx.main.ui.base.component.BaseViewHolder

class WalletTransactionAdapter : BaseAdapter<Transaction>() {

    override fun isEqual(left: Transaction, right: Transaction): Boolean {
        return left == right
    }

    override fun newViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Transaction> {
        return WalletTransaction(
                inflate(
                        parent,
                        R.layout.item_wallet_transaction
                )

        )
    }

    inner class WalletTransaction(binding: ViewDataBinding) : BaseViewHolder<Transaction>(binding) {
        private var mBinding = binding as ItemWalletTransactionBinding
        override fun bind(item: Transaction) {
            if (item.transactionType == 1) {
                mBinding.tvWalletName.text = String.format("Received from %s", item.senderWallet.name)
                mBinding.icIconAllActivity.setImageResource(R.drawable.ic_deposit)
            } else {
                mBinding.tvWalletName.text = String.format("Send to %s", item.receiverWallet.name)
                mBinding.icIconAllActivity.setImageResource(R.drawable.ic_withdraw)
            }
            mBinding.tvTransactionAmount.text = item.amount
            mBinding.tvWalletMessage.text = item.createdAt.replace(" ", "@")
        }
    }
}