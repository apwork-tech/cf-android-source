package com.wallet.cryptofuelx.main.ui.app.userprofile

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.wallet.cryptofuelx.R
import com.wallet.cryptofuelx.main.data.remote.response.profile.ProfileResponse
import com.wallet.cryptofuelx.main.ui.base.component.BaseActivity
import com.wallet.cryptofuelx.main.ui.base.helper.ProgressDialogUtils
import com.wallet.cryptofuelx.main.ui.base.setRipple
import com.wallet.cryptofuelx.utils.helper.Constants
import com.wallet.cryptofuelx.utils.helper.SharedPrefUtils
import com.wallet.cryptofuelx.utils.helper.imagepicker.ImageInfo
import com.wallet.cryptofuelx.utils.helper.imagepicker.ImagePickerUtils
import com.wallet.cryptofuelx.utils.libs.GlideUtils
import com.wallet.cryptofuelx.utils.libs.ToastUtils
import com.validator.easychecker.EasyChecker
import com.validator.easychecker.exceptions.DeveloperErrorException
import com.validator.easychecker.exceptions.InputErrorException
import kotlinx.android.synthetic.main.activity_user_profile.*
import java.io.File

class UserProfileActivity : BaseActivity<UserProfileView, UserProfilePresenter>(), UserProfileView, ImagePickerUtils.Listener {

    private var pickedImage: File? = null

    companion object {
        /**
         * This method starts current activity
         * @param context UI context
         * */
        fun startActivity(context: Context) {
            val intent = Intent(context, UserProfileActivity::class.java)
            runCurrentActivity(context, intent)
        }
    }

    override val layoutResourceId: Int
        get() = R.layout.activity_user_profile

    override fun getActivityPresenter(): UserProfilePresenter {
        return UserProfilePresenter()
    }

    override fun startUI() {
        //set ripple to views
        btn_pick_image.setRipple(R.color.colorWhite50)
        setListener()
        btn_save_changes.setRipple(R.color.colorWhite50)
        et_last_country_value.setDefaultCountryUsingNameCode("BD")
        et_last_country_value.resetToDefaultCountry()
        et_last_country_value.setTypeFace(ResourcesCompat.getFont(this,
                R.font.hind_vadodara_light))
        presenter.getUserProfileInfo()
    }


    private fun setListener() {

        btn_back?.setOnClickListener {
            finish()
        }

        image_view_edit.setOnClickListener {
            editProfile()
        }
        btn_save_changes.setOnClickListener {
            try {
                EasyChecker.validateInput(this,
                        5,
                        null,
                        et_first_name_value,
                        et_last_name_value,
                        et_last_phone_value).apply {
                    if (this) {
                        presenter.updateUserProfile(
                                et_first_name_value?.text.toString(),
                                et_last_name_value?.text.toString(),
                                et_last_phone_value?.text.toString(),
                                et_last_country_value?.selectedCountryName!!,
                                pickedImage)
                    }
                }
            } catch (ex: InputErrorException) {
                ToastUtils.error(ex.messageText)
            } catch (ex: DeveloperErrorException) {
                ex.printStackTrace()
            }
        }

        btn_pick_image?.setOnClickListener {
            ImagePickerUtils.pickImage(this, this)
        }


    }

    private fun editProfile() {
        btn_pick_image.visibility = View.VISIBLE
        layout_profile_edit.visibility = View.VISIBLE
        layout_profile_view.visibility = View.GONE
    }

    private fun showProfile() {
        btn_pick_image.visibility = View.INVISIBLE
        layout_profile_edit.visibility = View.GONE
        layout_profile_view.visibility = View.VISIBLE
    }

    override fun stopUI() {

    }

    override fun getProfileDataSuccess(profileResponse: ProfileResponse) {
        ProgressDialogUtils.on().hideProgressDialog()
        tv_first_name_value?.text = profileResponse.user.firstName
        tv_last_name_value?.text = profileResponse.user.lastName
        tv_email_value?.text = profileResponse.user.email
        tv_phone_value?.text = profileResponse.user.phone
        tv_country_value?.text = profileResponse.user.country
        et_first_name_value?.setText(profileResponse.user.firstName)
        et_last_name_value?.setText(profileResponse.user.lastName)
        et_last_phone_value?.setText(profileResponse.user.phone)
        et_last_country_value?.setDefaultCountryUsingNameCode(profileResponse.user.country)
        GlideUtils.normalWithCaching(
                image_view_user,
                profileResponse.user.photo
        )
    }

    override fun getProfileDataError(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.error(message)
    }

    override fun onSuccess(imageInfo: ImageInfo) {
        val imagePath = ImagePickerUtils.getImageRealPath(contentResolver!!,
                imageInfo.imageUri, null)
        pickedImage = File(imagePath)
        GlideUtils.normal(
                image_view_user,
                imageInfo.imageUri
        )
    }

    override fun onError(error: Throwable) {
        ToastUtils.error(error.message!!)
    }


    override fun onProfileUpdateSuccess(profileResponse: ProfileResponse) {
        ProgressDialogUtils.on().hideProgressDialog()
        showProfile()
        tv_first_name_value?.text = profileResponse.user.firstName
        tv_last_name_value?.text = profileResponse.user.lastName
        tv_email_value?.text = profileResponse.user.email
        tv_phone_value?.text = profileResponse.user.phone
        tv_country_value?.text = profileResponse.user.country
        GlideUtils.normalWithCaching(
                image_view_user,
                profileResponse.user.photo
        )
        SharedPrefUtils.write(Constants.PreferenceKeys.NAME, profileResponse.user.firstName)
        SharedPrefUtils.write(Constants.PreferenceKeys.IMAGE, profileResponse.user.photo)
    }

    override fun onProfileUpdateError(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.error(message)
    }
}