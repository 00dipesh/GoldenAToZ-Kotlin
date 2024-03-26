package com.goldendigitech.goldenatoz.login

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.goldendigitech.goldenatoz.MainActivity
import com.goldendigitech.goldenatoz.R
import com.goldendigitech.goldenatoz.databinding.ActivityLoginScreenBinding

class LoginScreen : AppCompatActivity() {

    val TAG = "PasswordActivity"

    lateinit var loginScreenBinding: ActivityLoginScreenBinding

    private lateinit var viewModel: LoginViewModel


    var PasswordVisible: Boolean = false
    @SuppressLint("ClickableViewAccessibility")
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

            val loginModel = LoginModel(email, password)
            viewModel.userLogin(loginModel)


        }

        viewModel.loginResponse.observe(this, Observer { response ->
            if (response != null) {
                if (response.success) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finishAffinity()
                    Log.e("LoginViewModel", "Login failed: ${response.success}")
                } else {
                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                    Log.e("LoginViewModelel", "Login failed:")

                }
            } else {
                Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
                Log.e("LoginViewModelek", "Login failed:")

            }
        })

    }




}