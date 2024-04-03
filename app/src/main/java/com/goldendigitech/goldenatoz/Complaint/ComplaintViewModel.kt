package com.goldendigitech.goldenatoz.Complaint

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goldendigitech.goldenatoz.attendance.TaskResponse
import com.goldendigitech.goldenatoz.serverConnect.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ComplaintViewModel :ViewModel() {

    private val webServices = Constant.webService

    private val _complaintResponse = MutableLiveData<ComplaintResponse>()
    val complaintResponse: LiveData<ComplaintResponse> = _complaintResponse

    fun addComplaints(complaintModel: ComplaintModel)
    {
        webServices.addComplaint(complaintModel).enqueue(object : Callback<ComplaintResponse>{
            override fun onResponse(
                call: Call<ComplaintResponse>,
                response: Response<ComplaintResponse>)
            {
                if (response.isSuccessful) {
                    _complaintResponse.value = response.body()
                    Log.e("ComplaintViewModel", "Complaint Success: ${_complaintResponse.value}")
                }
                else
                {
                    Log.e("ComplaintViewModel", "Complaint failed: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ComplaintResponse>, t: Throwable) {
                Log.e("ComplaintViewModel", "Network error: ${t.message}")
            }
        })
    }
}