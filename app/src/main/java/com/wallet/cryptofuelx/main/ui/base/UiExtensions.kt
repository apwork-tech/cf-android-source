package com.wallet.cryptofuelx.main.ui.base

import android.app.Activity
import android.content.res.ColorStateList
import android.graphics.drawable.RippleDrawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.wallet.cryptofuelx.main.ui.base.component.BasePresenter
import com.wallet.cryptofuelx.utils.helper.Constants
import com.wallet.cryptofuelx.utils.helper.DataUtils
import com.wallet.cryptofuelx.utils.helper.ViewUtils
import com.wallet.cryptofuelx.utils.helper.network.NetworkUtils
import com.wallet.cryptofuelx.utils.libs.GlideUtils

/**
 * This file is the collection of all the extension functions to be used in the UI
 * @author Mohd. Asfaq-E-Azam Rifat
 * */

fun View.isVisibile(): Boolean = visibility == View.VISIBLE

fun View.isGone(): Boolean = visibility == View.GONE

fun View.isInvisible(): Boolean = visibility == View.INVISIBLE

fun View.makeItVisible() {
    visibility = View.VISIBLE
}

fun View.makeItInvisible() {
    visibility = View.INVISIBLE
}

fun View.makeItGone() {
    visibility = View.GONE
}

fun RecyclerView.Adapter<*>.getString(stringResourceId: Int): String {
    return DataUtils.getString(stringResourceId)
}

fun BasePresenter<*>.getString(stringResourceId: Int): String {
    return DataUtils.getString(stringResourceId)
}

fun String.toTitleCase(): String {
    return DataUtils.toTitleCase(this)
}

fun ImageView.loadImage(source: Any) {
    GlideUtils.normalWithCaching(this, source)
}

fun ViewGroup.inflate(layoutId: Int): View? {
    return if (layoutId > Constants.Invalid.INVALID_INTEGER) {
        LayoutInflater.from(context).inflate(layoutId, this, false)
    } else {
        null
    }
}

fun Activity.isInternetAvailable(): Boolean {
    return NetworkUtils.isOnline()
}

fun Fragment.isInternetAvailable(): Boolean {
    return NetworkUtils.isOnline()
}

fun RecyclerView.Adapter<*>.isInternetAvailable(): Boolean {
    return NetworkUtils.isOnline()
}

fun BasePresenter<*>.isInternetAvailable(): Boolean {
    return NetworkUtils.isOnline()
}

fun View.setRipple(colorResourceId: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        background = RippleDrawable(ColorStateList.valueOf(ViewUtils.getColor(colorResourceId)),
                if (this is ImageView) null else background, null)
    }
}

fun Activity.setLightStatusBar() {
    ViewUtils.setLightStatusBar(this)
}

fun Activity.clearLightStatusBar() {
    ViewUtils.clearLightStatusBar(this)
}

fun Fragment.setLightStatusBar() {
    if (activity != null) {
        ViewUtils.setLightStatusBar(activity!!)
    }
}

fun Fragment.clearLightStatusBar() {
    if (activity != null) {
        ViewUtils.clearLightStatusBar(activity!!)
    }
}