package com.goldendigitech.goldenatoz.singleToneClass

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager private constructor(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "MySharedPreferences"
        private var instance: SharedPreferencesManager? = null

        @Synchronized
        fun getInstance(context: Context): SharedPreferencesManager {
            if (instance == null) {
                instance = SharedPreferencesManager(context.applicationContext)
            }
            return instance!!
        }
    }

    fun saveUserId(userId: Int) {
        sharedPreferences.edit().putInt("userId", userId).apply()
    }

    fun getUserId(): Int {
        return sharedPreferences.getInt("userId", 0)
    }


}