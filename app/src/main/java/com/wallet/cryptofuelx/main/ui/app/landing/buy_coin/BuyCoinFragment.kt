package com.wallet.cryptofuelx.main.ui.app.landing.buy_coin

import android.Manifest
import android.content.pm.PackageManager
import android.view.View
import android.widget.ArrayAdapter
import com.braintreepayments.api.models.Configuration
import com.wallet.cryptofuelx.R
import com.wallet.cryptofuelx.main.data.remote.response.bank_list.Bank
import com.wallet.cryptofuelx.main.data.remote.response.bank_list.BankList
import com.wallet.cryptofuelx.main.data.remote.response.buy_coin.BuyCoin
import com.wallet.cryptofuelx.main.ui.base.component.BaseFragment
import com.wallet.cryptofuelx.main.ui.base.helper.ProgressDialogUtils
import com.wallet.cryptofuelx.main.ui.base.setRipple
import com.wallet.cryptofuelx.utils.helper.Constants
import com.wallet.cryptofuelx.utils.helper.PermissionUtils
import com.wallet.cryptofuelx.utils.helper.imagepicker.ImageInfo
import com.wallet.cryptofuelx.utils.helper.imagepicker.ImagePickerUtils
import com.wallet.cryptofuelx.utils.libs.BraintreePaymentUtil
import com.wallet.cryptofuelx.utils.libs.ToastUtils
import com.validator.easychecker.EasyChecker
import com.validator.easychecker.exceptions.DeveloperErrorException
import com.validator.easychecker.exceptions.InputErrorException
import kotlinx.android.synthetic.main.fragment_buy_coin.*
import java.io.File

class BuyCoinFragment : BaseFragment<BuyCoinView, BuyCoinPresenter>(), BuyCoinView, BraintreePaymentUtil.PaymentListener {

    private var token: String? = null
    private var imageSleep: File? = null
    private var bankList: List<Bank> = ArrayList()

    override val layoutId: Int
        get() = R.layout.fragment_buy_coin

    override fun getFragmentPresenter(): BuyCoinPresenter {
        return BuyCoinPresenter()
    }

    override fun startUI() {
        setListener()
        presenter.getBankList()
    }

    private fun setListener() {
        payment_type_radio_group.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radio_btn_bank_deposit -> {
                    layout_buy_with_bank.visibility = View.VISIBLE
                    layout_buy_with_card.visibility = View.GONE
                }
                R.id.radio_btn_credit_card -> {
                    layout_buy_with_bank.visibility = View.GONE
                    layout_buy_with_card.visibility = View.VISIBLE
                }
            }
        }

        btn_pick_receipt.setOnClickListener {
            if (PermissionUtils.requestPermission(this,
                            PermissionUtils.REQUEST_CODE_PERMISSION_LOCATION_AND_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA
                    )) {
                ImagePickerUtils.pickImage(
                        this,
                        object : ImagePickerUtils.Listener {
                            override fun onSuccess(imageInfo: ImageInfo) {
                                imageSleep = File(ImagePickerUtils.getImageRealPath(activity!!.contentResolver,
                                        imageInfo.imageUri, null))
                                picked_file_name?.text = imageSleep?.name
                            }

                            override fun onError(error: Throwable) {
                                ToastUtils.error(error.message!!)
                            }
                        }
                )
            }

        }

        btn_buy_with_bank.setOnClickListener {
            if (et_amount?.text.toString().isNotEmpty()) {
                ProgressDialogUtils.on().showProgressDialog(activity!!)
                presenter.buyCoin(
                        Constants.PAYMENT_TYPE.BANK,
                        bankList[spinner_bank_list?.selectedItemPosition!!].id,
                        et_amount?.text.toString().toDouble(),
                        null,
                        imageSleep
                )
            } else {
                ToastUtils.info(getString(R.string.empty_amont))
            }
        }



        btn_buy_coin_with_card?.setOnClickListener {
            try {
                if (EasyChecker.validateInput(
                                activity!!,
                                0,
                                null,
                                edit_text_card_holder_name,
                                edit_text_card_number,
                                et_month,
                                et_year,
                                et_cvc
                        )) {
                    ProgressDialogUtils.on().showProgressDialog(activity!!)
                    BraintreePaymentUtil.payWithCreditCard(
                            activity!!,
                            getCardInfo(),
                            this
                    )
                }
            } catch (ex: InputErrorException) {
                ToastUtils.info(ex.messageText)
            } catch (ex: DeveloperErrorException) {
                ex.printStackTrace()
            }

        }

        btn_buy_with_bank.setRipple(R.color.colorWhite50)
        btn_buy_coin_with_card.setRipple(R.color.colorWhite50)
    }

    override fun stopUI() {

    }

    private fun getCardInfo(): HashMap<String, String> {
        val cardMap = HashMap<String, String>()
        cardMap[Constants.CARD_INFO.CARD_NUMBER] = edit_text_card_number.text.toString()
        cardMap[Constants.CARD_INFO.CARD_HOLDER_NAME] = edit_text_card_holder_name.text.toString()
        cardMap[Constants.CARD_INFO.CARD_CVC] = et_cvc.text.toString()
        cardMap[Constants.CARD_INFO.CARD_EXPIRE_YEAR] = et_year.text.toString()
        cardMap[Constants.CARD_INFO.CARD_EXPIRE_MONTH] = et_month.text.toString()
        return cardMap
    }

    override fun onBankListGetSuccess(bankList: BankList) {
        this.bankList = bankList.bankList
        ProgressDialogUtils.on().hideProgressDialog()
        var list: MutableList<String> = ArrayList()
        bankList.bankList.forEach {
            list.add(it.bankName)
        }
        spinner_bank_list?.adapter = object : ArrayAdapter<String>(activity!!,
                android.R.layout.simple_spinner_dropdown_item, list) {
            override fun getItem(position: Int): String? {
                return super.getItem(position)
            }
        }
        tv_btc_balance?.text = bankList.availableBalance.availableCoin.plus(bankList.availableBalance.coinName)
        tv_secondary_balance?.text = "$".plus(bankList.availableBalance.availableUsd)
    }

    override fun onBankListGetError(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.error(message)
    }

    override fun onBuyCoinSuccess(buyCoin: BuyCoin) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.success(buyCoin.message)
    }

    override fun onBuyCoinError(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.error(message)
    }

    override fun onConfig(config: Configuration) {

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var isGranted = true
        if (requestCode == PermissionUtils.REQUEST_CODE_PERMISSION_LOCATION_AND_STORAGE) {
            for (i in 0 until permissions.size) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    isGranted = false
                }
            }
            if (isGranted) {
                ImagePickerUtils.pickImage(
                        this,
                        object : ImagePickerUtils.Listener {
                            override fun onSuccess(imageInfo: ImageInfo) {
                                imageSleep = File(ImagePickerUtils.getImageRealPath(activity!!.contentResolver,
                                        imageInfo.imageUri, null))
                                picked_file_name?.text = imageSleep?.name
                            }

                            override fun onError(error: Throwable) {
                                ToastUtils.error(error.message!!)
                            }
                        }
                )
            } else {
                ToastUtils.warning("Picking image need permission!")
            }
        }
    }


    override fun onSuccess(token: String?) {
        if (et_amount?.text.toString().isNotEmpty()) {
            presenter.buyCoin(
                    Constants.PAYMENT_TYPE.CARD,
                    null,
                    et_amount?.text.toString().toDouble(),
                    token!!,
                    null
            )
        } else {
            ToastUtils.info(getString(R.string.empty_amont))
        }
    }

    override fun onError(error: String?) {
        ToastUtils.error(error!!)
    }

    override fun onCancel() {
        ToastUtils.info(getString(R.string.canel_by_user))
    }


}