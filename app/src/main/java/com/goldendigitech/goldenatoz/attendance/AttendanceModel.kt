package com.goldendigitech.goldenatoz.attendance

data class AttendanceModel (
    val signInTime: String,
    val signOutTime: String,
    val employeeId: Int,
    val employeeName: String,
    val hq: String,
    val employeeCode: String,
    val workingStatus: String,
    val workingFrom: String,
    val typesOfLeave: String,
    val fromDate: String,
    val toDate: String,
    val areaOfWork: String,
    val fileName: String,
    val fileType: String,
    val fileContent: String,
    val workingWith: String,
    val seniorId: Int,
    val juniorId: Int,
    val taskOfTheDay: String,
    val latitude: Double,
    val longitude: Double,
    val workingHr: String,
    val remark: String,
    val addressDetails: String
)