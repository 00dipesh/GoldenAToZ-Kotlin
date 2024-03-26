package com.goldendigitech.goldenatoz.attendance

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goldendigitech.goldenatoz.serverConnect.Constant
import kotlinx.coroutines.launch
import okhttp3.Request


class AttendanceViewModel : ViewModel()
{
    private val _response = MutableLiveData<AttendanceResponse>()
    val response: LiveData<AttendanceResponse> = _response


}
