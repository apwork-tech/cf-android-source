package com.wallet.cryptofuelx.main.ui.base

class GlobalValueHolder {

    companion object {
        public var scannedValue: Any? = null
        fun setScannedValue(s: String) {
            scannedValue = s
        }
    }

}