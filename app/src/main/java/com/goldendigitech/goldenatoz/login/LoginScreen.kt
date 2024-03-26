package com.goldendigitech.goldenatoz.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.goldendigitech.goldenatoz.R

class LoginScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //    setContentView(R.layout.activity_login_screen)
        loginScreenBinding = ActivityLoginScreenBinding.inflate(layoutInflater)
        val view: View = loginScreenBinding.root
        setContentView(view)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        Log.d(TAG,"Login Data: "+viewModel)
        loginScreenBinding.EdPassword.setOnTouchListener(View.OnTouchListener { view, motionEvent ->
            val correct = 2
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                if (motionEvent.rawX >= loginScreenBinding.EdPassword.right - loginScreenBinding.EdPassword.compoundDrawables[correct].bounds.width()) {
                    val selection = loginScreenBinding.EdPassword.selectionEnd
                    if (PasswordVisible) {
                        loginScreenBinding.EdPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_visibility_off,
                            0
                        )
                        loginScreenBinding.EdPassword.transformationMethod =
                            PasswordTransformationMethod.getInstance()
                        PasswordVisible = false
                    } else {
                        loginScreenBinding.EdPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_visibility,
                            0
                        )
                        loginScreenBinding.EdPassword.transformationMethod =
                            HideReturnsTransformationMethod.getInstance()
                        PasswordVisible = true
                    }
                    loginScreenBinding.EdPassword.setSelection(selection)
                }
            }
            false
        })

        loginScreenBinding.loginBtn.setOnClickListener {

            val email = loginScreenBinding.EdUsername.text.toString()
           // val contact = contactEditText.text.toString()
            val password = loginScreenBinding.EdPassword.text.toString()



    }
}