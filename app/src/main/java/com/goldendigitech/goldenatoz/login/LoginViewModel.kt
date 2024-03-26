package com.goldendigitech.goldenatoz.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goldendigitech.goldenatoz.serverConnect.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel:ViewModel() {

    private val webServices = Constant.webService

    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse

    fun userLogin(loginModel: LoginModel) {
        
        webServices.userLogin(loginModel).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.code() == 200) {
                    Log.d("LoginViewModel", "Login View Model: " + response.code())

                    _loginResponse.value = response.body()
                    Log.e("LoginViewModel", "Login failed: ${response.code()}")

                } else {

                    Log.e("LoginViewModel", "Login failed: ${response.code()}")

                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                // Handle network error
                Log.e("LoginViewModel", "Network error: ${t.message}")

            }
        })
    }

}