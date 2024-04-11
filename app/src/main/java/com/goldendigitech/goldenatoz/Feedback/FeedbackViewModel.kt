package com.goldendigitech.goldenatoz.Feedback

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goldendigitech.goldenatoz.serverConnect.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedbackViewModel : ViewModel() {
    private val webServices = Constant.webService
    private val _feedbackResponse = MutableLiveData<FeedbackResponse>()
    val feedbackResponse: LiveData<FeedbackResponse> = _feedbackResponse

    fun addFeedback(feedbackModel: FeedbackModel) {
        webServices.addFeedback(feedbackModel).enqueue(object : Callback<FeedbackResponse> {
            override fun onResponse(
                call: Call<FeedbackResponse>,
                response: Response<FeedbackResponse>) {
                if (response.isSuccessful) {
                    _feedbackResponse.value = response.body()
                    Log.e("FeedbackViewModel", "Feedback Success: ${_feedbackResponse.value}")
                }
                else
                {
                    Log.e("FeedbackViewModel", "Feedback failed: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<FeedbackResponse>, t: Throwable) {
                Log.e("FeedbackViewModel", "Network error: ${t.message}")

            }
        })
    }


}