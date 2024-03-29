package com.goldendigitech.goldenatoz.employee

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goldendigitech.goldenatoz.serverConnect.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmployeeViewModel : ViewModel() {

    private val webServices = Constant.webService

    private val _employeeLiveData = MutableLiveData<Employee?>()
    val employeeLiveData: LiveData<Employee?> = _employeeLiveData

    fun getEmployeeData(employeeId: Int) {
        webServices.getEmployee(employeeId).enqueue(object : Callback<EmployeeResponse> {
            override fun onResponse(
                call: Call<EmployeeResponse>,
                response: Response<EmployeeResponse>
            ) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse?.success == true) {
                        _employeeLiveData.value = apiResponse.data
                    } else {
                        // Handle unsuccessful response
                        Log.e("EmployeeViewModel", "Unsuccessful response: ${apiResponse?.message}")
                    }
                } else {
                    // Handle network error
                    Log.e("EmployeeViewModel", "Network error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EmployeeResponse>, t: Throwable) {
                // Handle network errors
                Log.e("EmployeeViewModel", "Network error: ${t.message}")
            }
        })
    }
}