package com.wallet.cryptofuelx.main.ui.app.all_activity

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.wallet.cryptofuelx.R
import com.wallet.cryptofuelx.databinding.ItemAllActivityBinding
import com.wallet.cryptofuelx.main.ui.app.all_activity.demo_model.BaseTransaction
import com.wallet.cryptofuelx.main.ui.base.component.BaseAdapter
import com.wallet.cryptofuelx.main.ui.base.component.BaseViewHolder
import com.wallet.cryptofuelx.utils.helper.ViewUtils

class AllActivityParentAdapter : BaseAdapter<BaseTransaction>() {

    override fun isEqual(left: BaseTransaction, right: BaseTransaction): Boolean {
        return left == right
    }

    override fun newViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<BaseTransaction> {
        return AllActivityHolder(
                inflate(parent,
                        R.layout.item_all_activity
                )
        )
    }

    inner class AllActivityHolder(binding: ViewDataBinding) : BaseViewHolder<BaseTransaction>(binding) {
        private var mBinding = binding as ItemAllActivityBinding
        override fun bind(item: BaseTransaction) {
            mBinding.tvActivityDate.text = item.date
            ViewUtils.initializeRecyclerView(
                    mBinding.recyclerViewAllActivityNested,
                    AllActivityNestedAdapter(),
                    null,
                    null,
                    LinearLayoutManager(mBinding.root.context),
                    null,
                    null,
                    null
            )
            getAllActivityNestedAdapter().addItems(item.transactionList)
        }

        private fun getAllActivityNestedAdapter(): AllActivityNestedAdapter {
            return mBinding.recyclerViewAllActivityNested.adapter as AllActivityNestedAdapter
        }
    }
}