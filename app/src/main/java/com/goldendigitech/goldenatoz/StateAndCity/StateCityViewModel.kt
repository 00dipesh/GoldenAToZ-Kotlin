package com.goldendigitech.goldenatoz.StateAndCity

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goldendigitech.goldenatoz.serverConnect.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class StateCityViewModel:ViewModel() {

    val statesLiveData: MutableLiveData<List<StateData>> = MutableLiveData()
    val citiesLiveData: MutableLiveData<List<String>> = MutableLiveData()

    fun fetchState() {
        val modelCall:Call<GetStateModel> = Constant.webService.GetState()
        modelCall.enqueue(object : Callback<GetStateModel> {
            override fun onResponse(call: Call<GetStateModel>, response: Response<GetStateModel>) {
                if (response.isSuccessful && response.body()!=null){
                    statesLiveData.value =response.body()?.Data
                }
            }
            override fun onFailure(call: Call<GetStateModel>, t: Throwable) {
                statesLiveData.value
            }
        })
    }

    fun fetchCity(stateId: Int) {
        val cityModelCall: Call<GetCityModel> = Constant.webService.getCitiesByStateId(stateId)
        cityModelCall.enqueue(object : Callback<GetCityModel> {
            override fun onResponse(call: Call<GetCityModel>, response: Response<GetCityModel>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.data != null) {
                        citiesLiveData.value = ArrayList(body.data.values)
                        Log.d("TAG", "AddTourModel response $response.body()")
                    } else {
                        // Handle empty response body
                        // For example, set citiesLiveData value to an empty list
                        citiesLiveData.value = emptyList()
                    }
                } else {
                    // Handle unsuccessful response
                    // For example, set citiesLiveData value to null
                    citiesLiveData.value
                }
            }

            override fun onFailure(call: Call<GetCityModel>, t: Throwable) {
                citiesLiveData.value
            }
        })
    }

//    fun getStatesLiveData(): MutableLiveData<List<StateData>> {
//        return statesLiveData
//    }
//
//    fun getCitiesLiveData(): MutableLiveData<List<String>> {
//        return citiesLiveData
//    }



}