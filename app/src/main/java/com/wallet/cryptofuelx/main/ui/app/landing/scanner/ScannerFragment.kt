package com.wallet.cryptofuelx.main.ui.app.landing.scanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wallet.cryptofuelx.main.ui.app.landing.container.ContainerActivity
import com.wallet.cryptofuelx.utils.helper.Constants
import me.dm7.barcodescanner.zxing.ZXingScannerView

class ScannerFragment : Fragment() {

    private var zXingScannerView: ZXingScannerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        zXingScannerView = ZXingScannerView(activity!!)
        return zXingScannerView
    }

    override fun onResume() {
        super.onResume()
        zXingScannerView?.startCamera()
        zXingScannerView!!.setResultHandler {
            val bundle = Bundle()
            bundle.putString(Constants.IntentKeys.WALLET_ADDRESS, it.text.toString())
            bundle.putString(Constants.IntentKeys.USD_BALANCE, arguments?.getString(Constants.IntentKeys.USD_BALANCE))
            bundle.putString(Constants.IntentKeys.BTC_BALANCE, arguments?.getString(Constants.IntentKeys.BTC_BALANCE))
            (activity!! as ContainerActivity).visitWithDraw(bundle)
        }
    }

    override fun onPause() {
        super.onPause()
        zXingScannerView?.stopCamera()
    }

}