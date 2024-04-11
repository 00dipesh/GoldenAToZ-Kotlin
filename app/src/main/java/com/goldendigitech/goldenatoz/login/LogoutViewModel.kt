package com.goldendigitech.goldenatoz.login

import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goldendigitech.goldenatoz.serverConnect.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LogoutViewModel :ViewModel() {

    private val webService = Constant.webService
//    lateinit var context: Context

    private val _logoutResponce =MutableLiveData<LogoutResponce>()
    val logoutResponce :LiveData<LogoutResponce> =_logoutResponce


    fun doLogoutUser(Email :String, Contact :String){

        webService.logoutUser(Email,Contact).enqueue(object :Callback<LogoutResponce> {
            override fun onResponse(
                call: Call<LogoutResponce>,
                response: Response<LogoutResponce>
            ) {
                if (response.isSuccessful){
                    val  logoutResponce =response.body()
                    val logoutmessage = logoutResponce?.message

                    if (logoutResponce?.success == true){
                        Log.d("TAG", "Logout Res: ${response.isSuccessful}")

                    }else{
                        Log.e("EmployeeViewModel", "Unsuccessful response: ${logoutmessage}")

                    }
                }else{
                    Log.e("EmployeeViewModel", "responce faild: ${response.message()}")

                }
            }

            override fun onFailure(call: Call<LogoutResponce>, t: Throwable) {
                Log.e("EmployeeViewModel", "Network error: ${t.message}")
            }

        })


    }




}