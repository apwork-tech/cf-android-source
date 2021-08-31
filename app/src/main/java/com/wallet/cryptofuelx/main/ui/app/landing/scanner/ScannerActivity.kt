package com.wallet.cryptofuelx.main.ui.app.landing.scanner

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import com.wallet.cryptofuelx.main.ui.base.Test
import com.wallet.cryptofuelx.main.ui.base.component.BaseActivity
import me.dm7.barcodescanner.zxing.ZXingScannerView

class ScannerActivity : AppCompatActivity() {

    companion object {
        /**
         * This method starts current activity
         * @param context UI context
         * */
        fun startActivity(context: Context) {
            BaseActivity.runCurrentActivity(context, Intent(context,
                    ScannerActivity::class.java))
        }
    }

    private var zXingScannerView: ZXingScannerView? = null

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        zXingScannerView = ZXingScannerView(this)
        setContentView(zXingScannerView)
        zXingScannerView!!.setResultHandler {
           Test.scannedValue = it.text
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        zXingScannerView?.startCamera()
    }

    override fun onPause() {
        super.onPause()
        zXingScannerView?.stopCamera()
    }
}