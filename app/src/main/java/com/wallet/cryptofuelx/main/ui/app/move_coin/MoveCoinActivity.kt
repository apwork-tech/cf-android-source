package com.wallet.cryptofuelx.main.ui.app.move_coin

import android.content.Context
import android.content.Intent
import com.wallet.cryptofuelx.R
import com.wallet.cryptofuelx.main.ui.base.component.BaseActivity

class MoveCoinActivity : BaseActivity<MoveCoinView, MoveCoinPresenter>() {

    companion object {
        /**
         * This method starts current activity
         * @param context UI context
         * */
        fun startActivity(context: Context) {
            val intent = Intent(context, MoveCoinActivity::class.java)
            runCurrentActivity(context, intent)
        }
    }

    override val layoutResourceId: Int
        get() = R.layout.activity_move_coin

    override fun getActivityPresenter(): MoveCoinPresenter {
        return MoveCoinPresenter()
    }

    override fun startUI() {

    }

    override fun stopUI() {

    }

}