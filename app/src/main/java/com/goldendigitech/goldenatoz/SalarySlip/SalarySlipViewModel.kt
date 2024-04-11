package com.goldendigitech.goldenatoz.SalarySlip

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goldendigitech.goldenatoz.serverConnect.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SalarySlipViewModel : ViewModel()
{
    private val webServices = Constant.webService
    private val _salarySlipResponse = MutableLiveData<List<EmployeeSalarySlip>?>()
    val salarySlipResponse: LiveData<List<EmployeeSalarySlip>?> = _salarySlipResponse

    fun showSalarySlip(Id : Int)
    {
        webServices.showSalarySlipList(Id).enqueue(object : Callback<SalarySlipResponse>
        {
            override fun onResponse(
                call: Call<SalarySlipResponse>,
                response: Response<SalarySlipResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse?.Success == true) {
                        _salarySlipResponse.value = apiResponse.Data
                    } else {
                        // Handle unsuccessful response
                        Log.e("SalarySlipViewModel", "Unsuccessful response: ${apiResponse?.Message}")
                    }
                } else {
                    // Handle network error
                    Log.e("SalarySlipViewModel", "Network error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SalarySlipResponse>, t: Throwable) {
                Log.e("SalarySlipViewModel", "Network error: ${t.message}")

            }
        })
    }
}

