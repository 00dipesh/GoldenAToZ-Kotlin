package com.goldendigitech.goldenatoz.Leave.Response

import com.google.gson.annotations.SerializedName

data class LeaveStatusResponse(
    @SerializedName("Success") var Success: Boolean,
    @SerializedName("Data") var Data: List<LeaveData>,
    @SerializedName("Message") var Message: String
)


data class LeaveData(
    @SerializedName("EmpId") var EmpId: Int,
    @SerializedName("EmpCode") var EmpCode: String,
    @SerializedName("LeaveType") var LeaveType: String,
    @SerializedName("LeavePeriod") var LeavePeriod: String,
    @SerializedName("FromDate") var FromDate: String,
    @SerializedName("ToDate") var ToDate: String,
    @SerializedName("Duration") var Duration: Int,
    @SerializedName("AproovalStatus") var AproovalStatus: String,
    @SerializedName("Remark") var Remark: String,
    @SerializedName("Id") var Id: Int,
    @SerializedName("IsActive") var IsActive: Boolean,
    @SerializedName("CreatedAt") var CreatedAt: String,
    @SerializedName("CreatedBy") var CreatedBy: Int,
    @SerializedName("UpdatedAt") var UpdatedAt: String,
    @SerializedName("UpdatedBy") var UpdatedBy: String

)