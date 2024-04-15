package com.goldendigitech.goldenatoz.Holiday

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goldendigitech.goldenatoz.employee.Employee
import com.goldendigitech.goldenatoz.serverConnect.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HolidayCalenderViewModel : ViewModel()
{
    private val webServices = Constant.webService

    private val _holidayCalenderLiveData = MutableLiveData<List<Holiday>?>()
    val holidayCalenderLiveData: LiveData<List<Holiday>?> = _holidayCalenderLiveData

    fun fetchHolidayCalendar()
    {
      webServices.showHolidayList().enqueue(object :Callback<HolidayResponse>{
          override fun onResponse(
              call: Call<HolidayResponse>,
              response: Response<HolidayResponse>
          ) {

                  if (response.isSuccessful) {
                      val apiResponse = response.body()
                      if (apiResponse?.success == true) {
                          _holidayCalenderLiveData.value = apiResponse.data
                      } else {
                          // Handle unsuccessful response
                          Log.e("HolidayCalenderViewModel", "Unsuccessful response: ${apiResponse?.message}")
                      }
                  } else {
                      // Handle network error
                      Log.e("HolidayCalenderViewModel", "Network error: ${response.message()}")
                  }

              }

          override fun onFailure(call: Call<HolidayResponse>, t: Throwable) {
              Log.e("HolidayCalenderViewModel", "Network error: ${t.message}")

          }
      })
    }

}