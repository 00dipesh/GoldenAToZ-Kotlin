package com.goldendigitech.goldenatoz.Leave.Response

import com.google.gson.annotations.SerializedName

data class AddLeaveModel(@SerializedName("EmpId") var EmpId : Int,
                         @SerializedName("EmpCode") var EmpCode : String,
                         @SerializedName("LeaveType") var LeaveType : String,
                         @SerializedName("LeavePeriod") var LeavePeriod : String,
                         @SerializedName("FromDate") var FromDate : String,
                         @SerializedName("ToDate") var ToDate : String,
                         @SerializedName("Remark") var Remark : String)
