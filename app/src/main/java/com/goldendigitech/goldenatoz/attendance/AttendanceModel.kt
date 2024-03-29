package com.goldendigitech.goldenatoz.attendance

import com.google.gson.annotations.SerializedName

data class AttendanceModel(
    @SerializedName("EmployeeId") val employeeId: Int,
    @SerializedName("EmployeeCode") val employeeCode: String,
    @SerializedName("WorkingStatus") val workingStatus: String,
    @SerializedName("WorkingFrom") val workingFrom: String,
    @SerializedName("AreaOfWork") val areaOfWork: String,
    @SerializedName("FileName") val fileName: String,
    @SerializedName("FileType") val fileType: String,
    @SerializedName("FileContent") val fileContent: String,
    @SerializedName("WorkingWith") val workingWith: String,
    @SerializedName("SeniorId") val seniorId: Int?,
    @SerializedName("JuniorId") val juniorId: Int?,
    @SerializedName("TaskOfTheDay") val taskOfTheDay: String,
    @SerializedName("Latitude") val latitude: Double,
    @SerializedName("Longitude") val longitude: Double,
    @SerializedName("Remark") val remark: String
)