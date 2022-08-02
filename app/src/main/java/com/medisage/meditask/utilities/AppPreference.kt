package com.medisage.meditask.utilities

import android.content.Context
import android.content.SharedPreferences

class AppPreference {

    companion object {
        private const val APP_NAME_KEY = "Medisage"
        private const val LOGIN_STATUS = "login_status"
        private const val USER_ID = "user_id"

        @Volatile
        private var preferences: SharedPreferences? = null

        @Volatile
        private var editor: SharedPreferences.Editor? = null

        private fun getInstance(context: Context): SharedPreferences {
            synchronized(this) {
                preferences =
                    context.getSharedPreferences(APP_NAME_KEY, Context.MODE_PRIVATE)
                return preferences!!
            }
        }

        fun setLoginStatus(context: Context, value: Int) {
            editor = getInstance(context).edit()
            editor!!.putInt(LOGIN_STATUS, value)
            editor!!.apply()
        }

        fun getLoginStatus(context: Context): Int {
            return getInstance(context).getInt(LOGIN_STATUS, 0)!!
        }

        fun setUserId(context: Context, value: String) {
            editor = getInstance(context).edit()
            editor!!.putString(USER_ID, value)
            editor!!.apply()
        }

        fun getUserId(context: Context): String {
            return getInstance(context).getString(USER_ID, "")!!
        }
    }

}