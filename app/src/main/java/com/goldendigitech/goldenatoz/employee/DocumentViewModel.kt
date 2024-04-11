package com.goldendigitech.goldenatoz.employee


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goldendigitech.goldenatoz.serverConnect.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DocumentViewModel : ViewModel() {

    private val webServices = Constant.webService
    private val _documentLiveData = MutableLiveData<List<Document>?>()
    val documentLiveData: LiveData<List<Document>?> = _documentLiveData

    fun getDocumentsByEmployeeId(employeeId: Int)
    {
        webServices.showFile(employeeId).enqueue(object : Callback<DocumentResponse>{
            override fun onResponse(
                call: Call<DocumentResponse>,
                response: Response<DocumentResponse>)
            {

                if(response.isSuccessful)
                {
                    val apiResponse = response.body()
                    if (apiResponse?.Success == true) {
                        _documentLiveData.value = apiResponse.Data
                    } else {
                        // Handle unsuccessful response
                        Log.e("DocumentViewModel", "Unsuccessful response: ${apiResponse?.Message}")
                    }
                }
             else {
                // Handle network error
                Log.e("DocumentViewModel", "Network error: ${response.message()}")
            }
            }

            override fun onFailure(call: Call<DocumentResponse>, t: Throwable) {
                Log.e("DocumentViewModel", "Network error: ${t.message}")
            }
        })
    }


}
