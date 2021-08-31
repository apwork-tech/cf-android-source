package com.wallet.cryptofuelx.utils.helper

import android.annotation.SuppressLint
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.util.Base64
import android.util.Log
import com.wallet.cryptofuelx.BaseApplication
import net.glxn.qrgen.android.QRCode
import timber.log.Timber
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.DecimalFormat
import java.util.*
import android.app.Activity
import android.content.*
import android.content.Context.CLIPBOARD_SERVICE
import android.widget.Toast
import android.content.Intent


/**
 * This is a class that contains utils to work with data
 * @author Mohd. Asfaq-E-Azam Rifat
 * */
class DataUtils private constructor() {
    companion object {
        val FACEBOOK_PACKAGE_NAME = "com.facebook.orca"
        val TWITTER_PACKAGE_NAME = "com.twitter.android"
        /**
         * This method provides an unique id using UUID
         *
         * @return [String] unique string
         * */
        fun getUniqueId(): String {
            return java.util.UUID.randomUUID().toString()
        }

        /*
        * This function generate qr code bitmap from text
        * */
        fun getQRCodeBitMapFromString(data: String): Bitmap? {
            return QRCode.from(data).bitmap()
        }

        /*
        * This function open website
        * */
        fun openLink(activity: Activity, link: String) {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(link)
            activity.startActivity(i)
        }

        /*
        * This function copy text to clipboard
        * */

        fun copyTextToClipBoard(context: Context, text: String) {
            var clipboard = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?
            val clip = ClipData.newPlainText("label", text)
            clipboard!!.setPrimaryClip(clip)
        }

        /**
         * This method provides a random number
         *
         * @param min minimum limit
         * @param max maximum limit
         * @return [Int] random number
         * */
        fun randomInt(min: Int, max: Int): Int {
            return Random().nextInt(max - min + 1) + min
        }

        /**
         * This method returns a local string
         *
         * @param resourceId desired resource id
         * @return desired string
         * */
        fun getString(resourceId: Int): String {
            return BaseApplication.getBaseApplicationContext().getString(resourceId)
        }

        /**
         * This method returns a local integer
         *
         * @param resourceId desired resource id
         * @return desired integer
         * */
        fun getInteger(resourceId: Int): Int {
            return ViewUtils.getResources().getInteger(resourceId)
        }

        /**
         * This method returns a local resource [Uri]
         *
         * @param resourceId desired resource id
         * @return desired [Uri]
         * */
        fun getUriFromResource(resourceId: Int): Uri {
            return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                    + AndroidUtils.getApplicationId() + "/" + resourceId)
        }


        /**
         * This function share information to facebook
         */

        fun shareToFacebook(context: Context, title: String) {
            val intent = Intent(Intent.ACTION_SEND)
            intent.setPackage(FACEBOOK_PACKAGE_NAME)
            intent.type = "text/plain"
            intent.putExtra(android.content.Intent.EXTRA_TEXT, title)
            try {
                // Start the specific social application
                context.startActivity(intent)
            } catch (ex: android.content.ActivityNotFoundException) {
                // The application does not exist
                Toast.makeText(context, "Messenger app have not been installed.", Toast.LENGTH_SHORT).show()
            }
        }

        /**
         * This function share information to twitter
         */
        fun shareToTwitter(context: Context, title: String) {
            val intent = Intent(Intent.ACTION_SEND)
            intent.setPackage(TWITTER_PACKAGE_NAME)
            intent.type = "text/plain"
            intent.putExtra(android.content.Intent.EXTRA_TEXT, title)
            try {
                // Start the specific social application
                context.startActivity(intent)
            } catch (ex: android.content.ActivityNotFoundException) {
                // The application does not exist
                Toast.makeText(context, "Twitter app have not been installed.", Toast.LENGTH_SHORT).show()
            }
        }


        /**
         * This method returns a converted title case string
         *
         * @param given given string
         * @return desired [String]
         * */
        fun toTitleCase(given: String): String {
            var isSpace = true
            val builder = StringBuilder(given)
            val len = builder.length

            for (i in 0 until len) {
                val char = builder[i]
                if (isSpace) {
                    if (!Character.isWhitespace(char)) {
                        // Convert to title case and switch out of whitespace mode.
                        builder.setCharAt(i, Character.toTitleCase(char))
                        isSpace = false
                    }
                } else if (Character.isWhitespace(char)) {
                    isSpace = true
                } else {
                    builder.setCharAt(i, Character.toLowerCase(char))
                }
            }

            return builder.toString()
        }

        @SuppressLint("InlinedApi", "LogNotTimber")
        fun getAndroidHashKey() {
            val context = BaseApplication.getBaseApplicationContext()
            try {
                val packageInfo: PackageInfo = context.packageManager.getPackageInfo(context.packageName,
                        if (AndroidUtils.getCurrentBuildApiVersion() >= Build.VERSION_CODES.P) {
                            PackageManager.GET_SIGNING_CERTIFICATES
                        } else {
                            PackageManager.GET_SIGNATURES
                        })

                var signatures = arrayOf<Signature>()

                if (AndroidUtils.getCurrentBuildApiVersion() >= Build.VERSION_CODES.P) {
                    if (packageInfo.signingInfo != null) {
                        signatures = packageInfo.signingInfo.apkContentsSigners
                    }
                } else {
                    signatures = packageInfo.signatures
                }

                for (signature in signatures) {
                    val messageDigest = MessageDigest.getInstance("SHA")
                    messageDigest.update(signature.toByteArray())
                    val hashKey = String(Base64.encode(messageDigest.digest(), 0))
                    Log.i(Constants.Common.ANDROID_HASH_KEY, hashKey)
                }
            } catch (e: NoSuchAlgorithmException) {
                Timber.e(e)
            } catch (e: Exception) {
                Timber.e(e)
            }
        }

        fun formatToShortNumber(number: Double): String {
            val suffix = arrayOf("", "k", "m", "b", "t")
            val MAX_LENGTH = 4
            var r = DecimalFormat("##0E0").format(number)
            r = r.replace("E[0-9]".toRegex(), suffix[Character.getNumericValue(r[r.length - 1]) / 3])
            while (r.length > MAX_LENGTH || r.matches("[0-9]+\\.[a-z]".toRegex())) {
                r = r.substring(0, r.length - 2) + r.substring(r.length - 1)
            }
            return r
        }
    }


}