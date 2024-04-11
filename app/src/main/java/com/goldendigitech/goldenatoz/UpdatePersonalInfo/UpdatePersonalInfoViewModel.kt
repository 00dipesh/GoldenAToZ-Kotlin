package com.goldendigitech.goldenatoz.UpdatePersonalInfo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goldendigitech.goldenatoz.serverConnect.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdatePersonalInfoViewModel : ViewModel() {
    private val webServices = Constant.webService
    private val _updatePersonalInfoResponse = MutableLiveData<UpdatePersonalInfoResponse>()
    val updatePersonalInfoResponse: LiveData<UpdatePersonalInfoResponse> =
        _updatePersonalInfoResponse

    fun updateUserPersonalInfo(updatePersonalInfoModel: UpdatePersonalInfoModel) {
        webServices.updateUserData(updatePersonalInfoModel)
            .enqueue(object : Callback<UpdatePersonalInfoResponse> {
                override fun onResponse(
                    call: Call<UpdatePersonalInfoResponse>,
                    response: Response<UpdatePersonalInfoResponse>
                ) {
                    if (response.isSuccessful) {
                        _updatePersonalInfoResponse.value = response.body()
                        Log.e(
                            "UpdatePersonalInfoViewModel",
                            "Update info Success: ${_updatePersonalInfoResponse.value}"
                        )
                    } else {
                        Log.e(
                            "UpdatePersonalInfoViewModel",
                            "Update info failed: ${response.code()}"
                        )
                    }
                }

                override fun onFailure(call: Call<UpdatePersonalInfoResponse>, t: Throwable) {
                    Log.e("UpdatePersonalInfoViewModel", "Network error: ${t.message}")
                }
            })
    }

}
