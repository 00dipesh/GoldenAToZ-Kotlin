package com.goldendigitech.goldenatoz.SalarySlip

data class SalarySlipResponse(val Success: Boolean,
                              val Data: List<EmployeeSalarySlip>,
                              val Message: String)

data class EmployeeSalarySlip(
    val EmployeeId: Int,
    val FileName: String,
    val FileType: String,
    val FileContent: String,
    val Id: Int,
    val IsActive: Boolean,
    val CreatedAt: String,
    val CreatedBy: Int
)
