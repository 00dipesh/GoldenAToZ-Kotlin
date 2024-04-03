package com.goldendigitech.goldenatoz.ChangePassword

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goldendigitech.goldenatoz.serverConnect.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangepasswordViewModel:ViewModel()
{
    private val webServices = Constant.webService
    private val _changepasswordResponse = MutableLiveData<ChangepasswordResponse>()
    val changepasswordResponse : LiveData<ChangepasswordResponse> = _changepasswordResponse

    fun updatePassword(changepasswordModel: ChangepasswordModel)
    {
        webServices.changePasswords(changepasswordModel).enqueue(object : Callback<ChangepasswordResponse>{
            override fun onResponse(
                call: Call<ChangepasswordResponse>,
                response: Response<ChangepasswordResponse>) {

                if (response.isSuccessful) {
                    _changepasswordResponse.value = response.body()
                    Log.e("ChangepasswordViewModel", "Changepassword Success: ${_changepasswordResponse.value}")
                }
                else
                {
                    Log.e("ChangepasswordViewModel", "Changepassword failed: ${response.code()}")
                }

            }

            override fun onFailure(call: Call<ChangepasswordResponse>, t: Throwable) {
                Log.e("ChangepasswordViewModel", "Network error: ${t.message}")

            }
        })
    }
}