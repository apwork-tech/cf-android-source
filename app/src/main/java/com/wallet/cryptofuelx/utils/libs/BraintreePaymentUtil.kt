package com.wallet.cryptofuelx.utils.libs

import android.app.Activity
import android.text.TextUtils
import com.braintreepayments.api.BraintreeFragment
import com.braintreepayments.api.Card
import com.braintreepayments.api.GooglePayment
import com.braintreepayments.api.PayPal
import com.braintreepayments.api.exceptions.ErrorWithResponse
import com.braintreepayments.api.interfaces.BraintreeCancelListener
import com.braintreepayments.api.interfaces.BraintreeErrorListener
import com.braintreepayments.api.interfaces.ConfigurationListener
import com.braintreepayments.api.interfaces.PaymentMethodNonceCreatedListener
import com.braintreepayments.api.models.CardBuilder
import com.braintreepayments.api.models.Configuration
import com.braintreepayments.api.models.GooglePaymentRequest
import com.braintreepayments.api.models.PayPalRequest
import com.google.android.gms.wallet.TransactionInfo
import com.google.android.gms.wallet.WalletConstants
import com.wallet.cryptofuelx.R
import com.wallet.cryptofuelx.utils.helper.Constants

/**
 * This is a singleton class that contains utils for payment via braintree
 *
 * N.B: You have to set the [R.string.tokenization_key] at first
 * @author Mohd. Asfaq-E-Azam Rifat
 * */
object BraintreePaymentUtil {

    private var mPaymentListener: PaymentListener? = null

    private val mPaymentMethodNonceCreatedListener = PaymentMethodNonceCreatedListener {
        mPaymentListener?.onSuccess(it.nonce)
    }

    private val mBraintreeCancelListener = BraintreeCancelListener {
        mPaymentListener?.onCancel()
    }

    private val mBraintreeErrorListener = BraintreeErrorListener {
        if (it is ErrorWithResponse) {
            val cardErrors = it.errorFor("creditCard")
            if (cardErrors != null) {
                if (!TextUtils.isEmpty(cardErrors.message)) {
                    mPaymentListener?.onError(cardErrors.message)
                }
                val expirationMonthError = cardErrors.errorFor("expirationMonth")
                if (expirationMonthError != null) {
                    if (!TextUtils.isEmpty(expirationMonthError.message)) {
                        mPaymentListener?.onError(expirationMonthError.message)
                    }
                }
            }
        }
    }

    private val mConfigurationListener = ConfigurationListener {
        mPaymentListener?.onConfig(it)
    }

    private var mBraintreeFragment: BraintreeFragment? = null

    private fun getBraintreeFragment(activity: Activity): BraintreeFragment {
        return BraintreeFragment.newInstance(activity, activity.getString(R.string.tokenization_key))
                .apply {
                    this.addListener(mPaymentMethodNonceCreatedListener)
                    this.addListener(mBraintreeCancelListener)
                    this.addListener(mBraintreeErrorListener)
                    this.addListener(mConfigurationListener)
                }
    }

    /**
     * This method removes listeners to avoid memory leak
     * */
    fun onDestroy() {
        mBraintreeFragment?.let {
            it.removeListener(mPaymentMethodNonceCreatedListener)
            it.removeListener(mBraintreeCancelListener)
            it.removeListener(mBraintreeErrorListener)
            it.removeListener(mConfigurationListener)
        }
        mBraintreeFragment = null
    }

    /**
     * This method let the user know if Google pay is ready to pay
     *
     * N.B: Please @see [onDestroy]. Put it under the current [Activity.onDestroy]
     *
     * @param activity current activity
     * @param listener listener to get state
     * */
    fun isReadyToPay(activity: Activity, listener: IsReadyListener?) {
        if (mBraintreeFragment == null) {
            mBraintreeFragment = getBraintreeFragment(activity)
        }
        GooglePayment.isReadyToPay(mBraintreeFragment) {
            listener?.onCheck(it)
        }
    }

    /**
     * This method is to pay a price via GooglePay
     *
     * N.B: Please @see [onDestroy]. Put it under the current [Activity.onDestroy]
     *
     * @param activity current activity
     * @param price required price
     * @param currencyCode required currency code
     * @param listener listener to get states
     * @param googleMerchantId id of the google merchant
     * */
    fun payViaGooglePay(activity: Activity, price: String, currencyCode: String,
                        listener: PaymentListener?, googleMerchantId: String = "") {

        if (mBraintreeFragment == null) {
            mBraintreeFragment = getBraintreeFragment(activity)
        }

        listener?.let {
            mPaymentListener = it
        }

        GooglePaymentRequest().transactionInfo(TransactionInfo.newBuilder()
                .setTotalPrice(price)
                .setTotalPriceStatus(WalletConstants.TOTAL_PRICE_STATUS_FINAL)
                .setCurrencyCode(currencyCode)
                .build())
                .billingAddressRequired(false)
                // Optional in sandbox;
                .googleMerchantId(googleMerchantId).apply {
                    mBraintreeFragment?.let {
                        GooglePayment.requestPayment(it, this)
                    }
                }
    }

    /**
     * fun this method pay with credit card
     */
    fun payWithCreditCard(activity: Activity,
                          cardMap: HashMap<String, String>,
                          listener: PaymentListener?) {
        if (mBraintreeFragment == null) {
            mBraintreeFragment = getBraintreeFragment(activity)
        }

        listener?.let {
            mPaymentListener = it
        }
        val cardBuilder = CardBuilder()
                .cardNumber(cardMap[Constants.CARD_INFO.CARD_NUMBER])
                .cardholderName(cardMap[Constants.CARD_INFO.CARD_HOLDER_NAME])
                .expirationMonth(cardMap[Constants.CARD_INFO.CARD_EXPIRE_MONTH])
                .expirationYear(cardMap[Constants.CARD_INFO.CARD_EXPIRE_YEAR])
                .cvv(cardMap[Constants.CARD_INFO.CARD_CVC])
        Card.tokenize(
                mBraintreeFragment, cardBuilder
        )
    }


    /**
     *fun this method pay with paypal
     */
    fun payWithPaypal(activity: Activity,
                      amount: String,
                      listener: PaymentListener?
    ) {
        if (mBraintreeFragment == null) {
            mBraintreeFragment = getBraintreeFragment(activity)
        }

        listener?.let {
            mPaymentListener = it
        }
        val request = PayPalRequest(amount)
                .currencyCode("USD")
                .intent(PayPalRequest.INTENT_AUTHORIZE)
        PayPal.requestOneTimePayment(mBraintreeFragment, request)
    }

    interface PaymentListener {
        fun onSuccess(token: String?)
        fun onError(error: String?)
        fun onCancel()
        fun onConfig(config: Configuration)
    }

    interface IsReadyListener {
        fun onCheck(isReady: Boolean)
    }
}