package com.goldendigitech.goldenatoz.Leave.Response

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goldendigitech.goldenatoz.serverConnect.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LeaveViewModel : ViewModel() {

    private val webServices = Constant.webService

    private val _leaveStatusLiveData = MutableLiveData<List<LeaveData>?>()
    val leaveStatusLiveData: LiveData<List<LeaveData>?> = _leaveStatusLiveData


    fun hanldeLeaveStatus(employeeId: Int, leaveStatus: String) {
        webServices.showLeaveStatus(employeeId, leaveStatus)
            .enqueue(object : Callback<LeaveStatusResponse> {
                override fun onResponse(
                    call: Call<LeaveStatusResponse>,
                    response: Response<LeaveStatusResponse>
                ) {
                    if (response.isSuccessful) {
                        val apiResponse = response.body()
                        if (apiResponse?.Success == true) {
                            _leaveStatusLiveData.value = apiResponse.Data
                        } else {
                            // Handle unsuccessful response
                            Log.e(
                                "LeaveViewModel",
                                "Unsuccessful response: ${apiResponse?.Message}"
                            )
                        }
                    } else {
                        // Handle network error
                        Log.e("LeaveViewModel", "Network error: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<LeaveStatusResponse>, t: Throwable) {
                    Log.e("LeaveViewModel", "Network error: ${t.message}")

                }
            })
    }


    private val _addLeaveResponse = MutableLiveData<AddLeaveResponse>()
    val addLeaveResponse: LiveData<AddLeaveResponse> = _addLeaveResponse


    fun handleAddLeave(addLeaveModel: AddLeaveModel) {
        webServices.addLeave(addLeaveModel).enqueue(object : Callback<AddLeaveResponse> {
            override fun onResponse(
                call: Call<AddLeaveResponse>,
                response: Response<AddLeaveResponse>
            ) {
                if (response.isSuccessful) {
                    _addLeaveResponse.value = response.body()
                    Log.e("LeaveViewModel", "Add Leave Success: ${_addLeaveResponse.value}")
                } else {
                    Log.e("LeaveViewModel", "Add Leave failed: ${response.code()}")
                }

            }

            override fun onFailure(call: Call<AddLeaveResponse>, t: Throwable) {
                Log.e("LeaveViewModel", "Network error: ${t.message}")

            }
        })
    }

}