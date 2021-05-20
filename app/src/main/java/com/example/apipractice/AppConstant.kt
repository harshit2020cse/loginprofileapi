package com.example.apipractice

object AppConstant {

    // const val BASE_URL: String = "http://medoplus.api.relinns.com.au/"
    const val BASE_URL: String = "https://stage.api.medoplus.org/"

    const val KEY = "KEY"

    /**
     * API Param Keys
     *
     * Note: These Keys are dependent of Configuration defined by Backend in APIs
     * Don't Change it without permission
     * */
    interface API_PARAM_KEY {
        companion object {
            val SESSION = "session"
            val PASSWORD = "password"
            val USERNAME = "username"
            val DEVICE = "device"
            val DEVICE_ID = "deviceId"
            val FCM_TOKEN = "fcmToken"
            val NOTIFICATIONS_ENABLED = "notificationsEnabled"
            val ANDROID = "ANDROID"
        }
    }

    /**
     * User Type Keys
     *
     * Note: These Keys are dependent of Configuration defined by Backend in APIs
     * Don't Change it without permission
     * */
    interface USER_TYPE {
        companion object {
            val PATIENT = "PATIENT"
        }
    }

    /**
     * AUTHENTICATION Param Keys
     * */
    interface AUTHENTICATION_KEYS {
        companion object {
            val LOGOUT = "logout"
            val KEY = "KEY"
        }
    }

    /**
     * EDITPROFILE Param Keys
     * */
    interface EDITPROFILE {
        companion object {
            val EDIT_PROFILE = "logout"
            val KEY = "KEY"
        }
    }

    /** Banner Type Keys
     *
     * These Keys are dependent of Configuration defined by Backend in APIs
     * Don't Change it without permission
     * */
    interface BANNER_TYPE {
        companion object {
            const val TERMS = "TERMS_CONDITION"
            const val PRIVACY = "PRIVACY_POLICY"
            const val CONTACT_US = "CONTACT_US"
            const val ABOUT = "ABOUT_US"
            const val HOME = "HOME"
            const val REFUND_POLICY = "REFUND_POLICY"
            const val CANCELLATION = "CANCELLATION"
        }
    }

}