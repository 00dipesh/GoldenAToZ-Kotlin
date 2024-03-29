package com.goldendigitech.goldenatoz.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.preference.PreferenceManager
import android.util.Log
import com.goldendigitech.goldenatoz.MainActivity
import com.goldendigitech.goldenatoz.R
import com.goldendigitech.goldenatoz.attendance.AttendanceActivity
import com.goldendigitech.goldenatoz.login.LoginScreen
import com.goldendigitech.goldenatoz.singleToneClass.SharedPreferencesManager

class SplashScreen : AppCompatActivity() {

    companion object {
        const val TAG = "SplashScreen"
    }

    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Initialize SharedPreferencesManager
        sharedPreferencesManager = SharedPreferencesManager.getInstance(this)


        val userId = sharedPreferencesManager.getUserId()  // Retrieve user ID
        Log.d(TAG, "Splash userid: $userId")

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            if (userId == 0) {
                // If user ID is not found, navigate to login activity
                Log.d(TAG, "User ID not found in SharedPreferences")
                val intent = Intent(this, LoginScreen::class.java)
                startActivity(intent)
            } else {
                // If user ID is found, navigate to main activity
                Log.d(TAG, "User ID found: $userId")
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            finish()
        }, 2000)
    }
}