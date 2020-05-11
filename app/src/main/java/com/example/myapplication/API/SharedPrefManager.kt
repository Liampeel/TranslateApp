package com.example.myapplication.API

import android.content.Context
import android.content.SharedPreferences
import com.example.myapplication.Models.loginResponse
import com.example.myapplication.R


class SharedPrefManager private constructor(private val mCtx: Context) {

        fun saveAuthToken(token: String) {
            val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("token", token)
            editor.apply()
    }

    /**
     * Function to fetch auth token
     */
    fun fetchAuthToken(): String? {
        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString("token", null)
    }

    fun clear() {
        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    companion object {
        private val SHARED_PREF_NAME = "my_shared_preff"
        private var mInstance: SharedPrefManager? = null
        @Synchronized
        fun getInstance(mCtx: Context): SharedPrefManager {
            if (mInstance == null) {
                mInstance = SharedPrefManager(mCtx)
            }
            return mInstance as SharedPrefManager
        }
    }

}







//class SharedPrefManager (context: Context) {
//    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
//    private var mInstance: SharedPrefManager? = null
//
//    companion object {
//        const val USER_TOKEN = "user_token"
//    }
//
//    /**
//     * Function to save auth token
//     */
//    fun saveAuthToken(token: String) {
//        val editor = prefs.edit()
//        editor.putString(USER_TOKEN, token)
//        editor.apply()
//    }
//
//    /**
//     * Function to fetch auth token
//     */
//    fun fetchAuthToken(): String? {
//        return prefs.getString(USER_TOKEN, null)
//    }
//
//
//    fun clear() {
//        val editor = prefs.edit()
//        editor.clear()
//        editor.apply()
//    }
//
//    @Synchronized
//    fun getInstance(context: Context): SharedPrefManager? {
//        if (mInstance == null) {
//            mInstance = SharedPrefManager(context)
//        }
//        return mInstance
//    }
//
//}