package com.wallet.cryptofuelx.utils.helper

import com.wallet.cryptofuelx.R

/**
 * This is a class that contains all the needed constants
 * @author Mohd. Asfaq-E-Azam Rifat
 * */
class Constants {
    class Invalid {
        companion object {
            const val INVALID_INTEGER: Int = -1
            const val INVALID_LONG: Long = -1
        }
    }

    class PAYMENT_TYPE {
        companion object {
            const val CARD = 2
            const val BANK = 4
        }
    }


    class Default {
        companion object {
            const val DEFAULT_STRING: String = ""
            const val DEFAULT_INTEGER: Int = 0
            const val DEFAULT_LONG: Long = 0
            const val DEFAULT_BOOLEAN: Boolean = false
            const val DEFAULT_LANGUAGE: String = "en"
            const val TRUE_INTEGER: Int = 1
            const val FALSE_INTEGER: Int = 0
            const val SPACE_STRING: String = " "
        }
    }

    class Common {
        companion object {
            const val ANDROID_HASH_KEY = "Hash Key"
            const val COMMON_TIME_ZONE = "UTC"
            const val APP_COMMON_DATE_FORMAT: String = "dd MMMM, yyyy"
            const val APP_COMMON_ONLY_DATE_FORMAT: String = "dd MMMM, yyyy"
            const val APP_COMMON_TIME_FORMAT: String = "hh:mm a"
            const val APP_COMMON_DAY_FORMAT: String = "E"
            const val APP_COMMON_MONTH_FORMAT: String = "MMM"
            const val ROLE = "Bearer "
            const val TELEPHONE_URI_STARTING = "tel:"
            const val BASE_URL_APP_RESOURCES = "file:///android_res/"
            const val HTML_JUSTIFIED_STYLE = "<html>" +
                    "<style type='text/css'>" +
                    "@font-face {" +
                    "font-family: MyFont;" +
                    "src: url('font/raleway_regular.ttf')" +
                    "}" +
                    "body {" +
                    "margin: 0;" +
                    "line-height: 1.5;" +
                    "padding: 0;" +
                    "font-family: MyFont;" +
                    "font-size: 14px;" +
                    "text-align: justify;" +
                    "color: #7E7777" +
                    "}" +
                    "</style>" +
                    "<body>%s" +
                    "</body>" +
                    "</html>"
            const val HTML_SMALL_JUSTIFIED_STYLE = "<html>" +
                    "<style type='text/css'>" +
                    "@font-face {" +
                    "font-family: MyFont;" +
                    "src: url('font/raleway_regular.ttf')" +
                    "}" +
                    "body {" +
                    "margin: 0;" +
                    "line-height: 1.5;" +
                    "padding: 0;" +
                    "font-family: MyFont;" +
                    "font-size: 12px;" +
                    "text-align: justify;" +
                    "color: #FFFFFF" +
                    "}" +
                    "</style>" +
                    "<body>%s" +
                    "</body>" +
                    "</html>"
            const val HTML_MIME_TYPE = "text/html"
            const val HTML_ENCODING = "UTF-8"
        }
    }

    class TableNames {
        companion object {
            const val USER = "USER"
        }
    }

    class ColumnNames {
        companion object {
            const val ID = "ID"
            const val USER_ID = "USER_ID"
            const val USER_NAME = "USER_NAME"
        }
    }

    class ChartType {
        companion object {
            const val TYPE_MONTH = 1
            const val TYPE_WEEK = 2
        }
    }

    class File {
        companion object {
            val DIRECTORY_ROOT = DataUtils.getString(R.string.app_name)
            val PREFIX_IMAGE = "IMG_"
            val PREFIX_CROPPED_IMAGE = "IMG_CROPPED_"
            val SUFFIX_IMAGE = ".jpg"
        }
    }

    class JsonKeys {
        companion object {
            const val SUCCESS = "success"
            const val DATA = "data"
            const val TYPES = "types"
            const val MESSAGE = "message"
            const val ACCESS_TOKEN = "access_token"
            const val USER_INFO = "user_info"
            const val NAME = "first_name"
            const val EMAIL = "email"
            const val NUMBER = "number"
            const val MOBILE = "mobile"
            const val FCM_TOKEN = "fcm_registration_id"
            const val ID = "id"
            const val LATITUDE = "latitude"
            const val LONGITUDE = "longitude"
            const val IS_VERIFIED = "email_verified"
            const val ADDRESS = "address"
            const val CITY = "city"
            const val ZIP_CODE = "zip_code"
            const val DAY = "day"
            const val STARTING_TIME = "start"
            const val ENDING_TIME = "end"
            const val IS_HOLIDAY = "is_holiday"
            const val IMAGE = "photo"
            const val RESERVATION_TIME = "reservation_time"
            const val BODY_CONTENT = "body_content"
            const val TOP_LEFT_QUANTITY = "top_left_quantity"
            const val TOP_LEFT = "top_left"
            const val TOP_MIDDLE_QUANTITY = "top_middle_quantity"
            const val TOP_MIDDLE = "top_middle"
            const val TOP_RIGHT_QUANTITY = "top_right_quantity"
            const val TOP_RIGHT = "top_right"
            const val SCHEDULED_ON = "scheduled_on"
            const val CLINIC = "clinic"
            const val SERVICE = "service"
            const val SESSION = "session"
            const val STATUS = "status"
        }
    }

    class PreferenceKeys {
        companion object {
            const val FCM_TOKEN = "fcm_token"
            const val LOGGED_IN = "logged_in"
            const val IS_FIRST_TIME = "is_first_time"
            const val ACCESS_TOKEN = "access_token"
            const val NAME = "name"
            const val EMAIL = "email"
            const val MOBILE = "mobile"
            const val IMAGE = "image"
        }
    }

    class CARD_INFO {
        companion object {
            const val CARD_HOLDER_NAME = "holder_name"
            const val CARD_NUMBER = "card_number"
            const val CARD_CVC = "card_cvc"
            const val CARD_EXPIRE_YEAR = "expire_year"
            const val CARD_EXPIRE_MONTH = "expire_month"
        }
    }

    class TIME_PREIOD {
        companion object {
            const val WEEK = "week"
            const val MONTH = "month"
        }
    }


    class IntentKeys {
        companion object {
            const val CLINIC_ID = "clinic_id"
            const val SERVICE_ID = "service_id"
            const val EMAIL = "email"
            const val MONTH_LIMIT = "month_limit"
            const val CONTENT_POSITION = "content_position"
            const val WALLET_ID = "wallet_id"
            const val WALLET_ADDRESS = "wallet_address"
            const val BTC_BALANCE = "btc_balance"
            const val USD_BALANCE = "usd_balance"
        }
    }

    class API {
        companion object {
            const val REGISTER = "sign-up"
            const val LOGIN = "login"
            const val VERIFY_EMAIL = "email-verify"
            const val RESET_PASSWORD = "reset-password"
            const val CLINICS = "clinics"
            const val APPOINTMENT_TYPE = "appointment/type"
            const val BOOK_APPOINTMENT = "book/appointment"
            const val RESERVATION_TIME_LIMIT = "reservation/time"
            const val AVAILABILITIES = "availabilities"
            const val CONTACT_US = "contact/us"
            const val ABOUT_US = "about/page"
            const val APPOINTMENTS = "appointments"
            const val CANCEL_APPOINTMENT = "appointments/cancel"
            const val LOG_OUT = "logout"
            const val PROFILE_DETAILS = "profile"
            const val UPDATE_PASSWORD = "update-password"
            const val UPDATE_USER_PROFILE = "update-profile"
            const val WITHDRAW_BALANCE = "withdrawal-balance"
            const val MY_REFERRAL = "my-referral"
            const val WALLET_LIST = "my-wallet-list"
            const val BANK_LIST = "bank-list"
            const val FORGOT_PASSWORD = "send-reset-password-code"
            const val CREATE_WALLET = "create-wallet"
            const val BUY_COIN = "buy-coin"
            const val WALLET_HISTORY = "wallet-transaction-history-"
            const val GENERATE_WALLET_ADDRESS = "generate-wallet-address"
            const val CHANGE_PASSWORD = "change-password"
            const val HOME = "home"
        }

        class Header {
            companion object {
                const val RESPONSE_FORMAT = "Accept:application/json"
            }

            class Field {
                companion object {
                    const val AUTHORIZATION = "Authorization"
                }
            }
        }


        class Body {
            class Field {
                companion object {
                    const val ID = "id"
                    const val FIRST_NAME = "first_name"
                    const val LAST_NAME = "last_name"
                    const val EMAIL = "email"
                    const val MESSAGE = "message"
                    const val MOBILE = "phone"
                    const val PASSWORD = "password"
                    const val COUNTRY = "country"
                    const val CONFRIM_PASSWORD = "password_confirmation"
                    const val ACCESS_CODE = "access_code"
                    const val DEVICE_TYPE = "device_type"
                    const val FCM_TOKEN = "fcm_registration_id"
                    const val CLINIC_ID = "clinic_id"
                    const val SESSION_ID = "session_id"
                    const val SERVICE_ID = "service_id"
                    const val DATE = "date"
                    const val IS_UPCOMING = "is_upcoming"
                    const val WALLET_NAME = "name"
                    const val WALLET_ID = "wallet_id"
                    const val ADDRESS = "address"
                    const val AMOUNT = "amount"
                    const val PAYMENT_TYPE = "payment_type"
                    const val COIN = "coin"
                    const val PAYMENT_METHOD_NONCE = "payment_method_nonce"
                    const val SLEEP = "sleep"
                    const val BANK_ID = "bank_id"
                    const val CURRENT_PASS = "old_password"
                    const val NEW_PASSWOD = "password"
                    const val REPEAT_PASSWORD = "password_confirmation"
                    const val TIME_PREIOD = "time_period"
                }
            }
        }
    }

    class SelectionIds {
        companion object {
            const val APPOINTMENT_TYPE = "AppointmentType"
            const val CLINIC = "Clinic"
            const val SESSION = "Session"
        }
    }

    class LanguageCodes {
        companion object {
            const val ENGLISH = "en"
            const val PORTUGUESE = "pt"
        }
    }

    class HTTP_CODE {
        companion object {
            const val UNAUTHENTICATED = 401
        }
    }
}