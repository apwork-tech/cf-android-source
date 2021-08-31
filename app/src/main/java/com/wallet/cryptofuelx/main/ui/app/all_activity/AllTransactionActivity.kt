package com.wallet.cryptofuelx.main.ui.app.all_activity

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.wallet.cryptofuelx.R
import com.wallet.cryptofuelx.main.ui.app.all_activity.demo_model.BaseTransaction
import com.wallet.cryptofuelx.main.ui.app.all_activity.demo_model.Transaction
import com.wallet.cryptofuelx.main.ui.base.component.BaseActivity
import com.wallet.cryptofuelx.utils.helper.ViewUtils
import kotlinx.android.synthetic.main.activity_all_transaction.*

class AllTransactionActivity : BaseActivity<AllActivityView, AllTransactionPresenter>() {

    companion object {
        /**
         * This method starts current activity
         * @param context UI context
         * */
        fun startActivity(context: Context) {
            val intent = Intent(context, AllTransactionActivity::class.java)
            runCurrentActivity(context, intent)
        }
    }

    override val layoutResourceId: Int
        get() = R.layout.activity_all_transaction

    override fun getActivityPresenter(): AllTransactionPresenter {
        return AllTransactionPresenter()
    }

    private fun initializeAllTransactionList() {
        ViewUtils.initializeRecyclerView(
                recycler_view_all_activity,
                AllActivityParentAdapter(),
                null,
                null,
                LinearLayoutManager(this),
                null,
                null,
                null
        )
    }


    private fun getAllActivityParentAdpater(): AllActivityParentAdapter {
        return recycler_view_all_activity.adapter as AllActivityParentAdapter
    }

    override fun startUI() {
        initializeAllTransactionList()
        var transactionListOne = Transaction(R.drawable.ic_activity, "Jony's Wallet",
                "Received from Mujahid", "000.35434300BTC")
        var transactionListTwo = Transaction(R.drawable.ic_activity, "Jony's Wallet",
                "Received from Mujahid", "000.35434300BTC")
        var transactionListThree = Transaction(R.drawable.ic_activity, "Jony's Wallet",
                "Received from Mujahid", "000.35434300BTC")
        var transactionListFour = Transaction(R.drawable.ic_activity, "Jony's Wallet",
                "Received from Mujahid", "000.35434300BTC")
        var transactionListFive = Transaction(R.drawable.ic_activity, "Jony's Wallet",
                "Received from Mujahid", "000.35434300BTC")
        var transactionListSix = Transaction(R.drawable.ic_activity, "Jony's Wallet",
                "Received from Mujahid", "000.35434300BTC")
        val transactionList = ArrayList<Transaction>()
        transactionList.add(transactionListOne)
        transactionList.add(transactionListTwo)
        transactionList.add(transactionListThree)
        transactionList.add(transactionListFour)
        transactionList.add(transactionListFive)
        transactionList.add(transactionListSix)
        var baseTransaction = BaseTransaction("02-11-2019", transactionList)
        var baseTransactionTwo = BaseTransaction("03-11-2019", transactionList)
        var baseTransactionThree = BaseTransaction("04-11-2019", transactionList)
        var baseTransactionTwoFour = BaseTransaction("05-11-2019", transactionList)
        val baseTransactionList = ArrayList<BaseTransaction>()
        baseTransactionList.add(baseTransaction)
        baseTransactionList.add(baseTransactionTwo)
        baseTransactionList.add(baseTransactionThree)
        baseTransactionList.add(baseTransactionTwoFour)
        getAllActivityParentAdpater().addItems(baseTransactionList)
    }

    override fun stopUI() {

    }

}