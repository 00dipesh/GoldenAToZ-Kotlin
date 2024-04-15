package com.goldendigitech.goldenatoz.Leave

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.goldendigitech.goldenatoz.ChangePassword.ChangepasswordModel
import com.goldendigitech.goldenatoz.Leave.Response.AddLeaveModel
import com.goldendigitech.goldenatoz.Leave.Response.LeaveViewModel
import com.goldendigitech.goldenatoz.MainActivity
import com.goldendigitech.goldenatoz.databinding.ActivityLeaveApplicationBinding
import com.goldendigitech.goldenatoz.employee.Employee
import com.goldendigitech.goldenatoz.employee.EmployeeViewModel
import com.goldendigitech.goldenatoz.singleToneClass.SharedPreferencesManager
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class LeaveApplication : AppCompatActivity() {

    private lateinit var leaveApplicationBinding: ActivityLeaveApplicationBinding
    private lateinit var employeeViewModel: EmployeeViewModel
    private lateinit var leaveViewModel: LeaveViewModel
    private var searchDate: String = ""
    private var empcode: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        leaveApplicationBinding = ActivityLeaveApplicationBinding.inflate(layoutInflater)
        val view: View = leaveApplicationBinding.root
        setContentView(view)


        employeeViewModel = ViewModelProvider(this).get(EmployeeViewModel::class.java)
        leaveViewModel = ViewModelProvider(this).get(LeaveViewModel::class.java)

        val employeeId = SharedPreferencesManager.getInstance(this).getUserId()

        setDefaultDatetoSearch()

        employeeViewModel.getEmployeeData(employeeId)
        employeeViewModel.employeeLiveData.observe(this) { employee ->
            employee?.let {
                updateUI(employee)
            } ?: run {
                Toast.makeText(
                    this@LeaveApplication,
                    "Failed to get employee details",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


        // Date picker dialog for "To" date
        leaveApplicationBinding.edTodate.setOnClickListener {
            val cal = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    searchDate = "$year-${month + 1}-$dayOfMonth"
                    leaveApplicationBinding.edTodate.setText(formatDate(year, month, dayOfMonth))
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            )
//            datePickerDialog.datePicker.maxDate = cal.timeInMillis
            datePickerDialog.show()
        }

       // Date picker dialog for "From" date
        leaveApplicationBinding.edFromdate.setOnClickListener {
            val cal = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    searchDate = "$year-${month + 1}-$dayOfMonth"
                    leaveApplicationBinding.edFromdate.setText(formatDate(year, month, dayOfMonth))
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

        leaveApplicationBinding.ivBack.setOnClickListener {
            val intent = Intent(this, LeaveStatus::class.java)
            startActivity(intent)
            finishAffinity()
        }

        leaveApplicationBinding.btnSubmit.setOnClickListener {
            val lType = leaveApplicationBinding.spLeavetype.selectedItem.toString()
            val lPeriod = leaveApplicationBinding.spPeriod.selectedItem.toString()
            val lfromDate = leaveApplicationBinding.edFromdate.text.toString()
            val ltoDate = leaveApplicationBinding.edTodate.text.toString()
            val lremarks = leaveApplicationBinding.edReasons.text.toString()



            val validationMessage = validateLeaveFields(lType, lPeriod, lfromDate,ltoDate,lremarks)
            if (validationMessage != null) {
                // Display validation error message
                Toast.makeText(this, validationMessage, Toast.LENGTH_SHORT).show()

            } else {
                // Validation successful, proceed with password update
                val request = AddLeaveModel(employeeId,empcode,lType, lPeriod, lfromDate,ltoDate,lremarks)
               leaveViewModel.handleAddLeave(request)
            }
        }

        leaveViewModel.addLeaveResponse.observe(this, Observer { responseAdd->
            if (responseAdd != null) {
                Log.e("LeaveApplication", "LeaveApplication Success: ${responseAdd}")

                if (responseAdd.Success) {
                    Toast.makeText(this, responseAdd.Message, Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LeaveStatus::class.java)
                    startActivity(intent)
                    finishAffinity()
                } else {

                    Toast.makeText(this, responseAdd.Message, Toast.LENGTH_SHORT).show()
                }
            } else {

                Toast.makeText(this, "Null response received", Toast.LENGTH_SHORT).show()
            }

        })

    }


    private fun setDefaultDatetoSearch() {
        val dateFormat: DateFormat = SimpleDateFormat("dd-MMM-yy")
        val date = Date()
        val formattedDate = dateFormat.format(date)

        leaveApplicationBinding.edTodate.text = Editable.Factory.getInstance().newEditable(formattedDate)
        leaveApplicationBinding.edFromdate.text = Editable.Factory.getInstance().newEditable(formattedDate)

        val dbFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        searchDate = dbFormat.format(date)
    }

    private fun formatDate(year: Int, month: Int, day: Int): String {
        val cal = Calendar.getInstance().apply {
            clear()
            set(year, month, day)
        }
        val sdf = SimpleDateFormat("dd-MMM-yy", Locale.getDefault())
        return sdf.format(cal.time)
    }

    private fun updateUI(employee: Employee) {
        val firstName = employee.firstName
        val middleName = employee.middleName
        val lastName = employee.lastName
        empcode = employee.employeeCode

        val fullName = "$firstName $middleName $lastName"
        leaveApplicationBinding.edName.text = Editable.Factory.getInstance().newEditable(fullName)
        leaveApplicationBinding.edEmpid.text = Editable.Factory.getInstance().newEditable(empcode)
    }

    private fun validateLeaveFields(
        leaveType: String,
        leavePeriod: String,
        fromDate: String,
        toDate: String,
        remark: String
    ): String? {
        // Perform validation checks
        if (leaveType.isEmpty()) {
            return "Leave type cannot be empty."
        }
        if (leavePeriod.isEmpty()) {
            return "Leave period cannot be empty."
        }
        if (fromDate.isEmpty()) {
            return "From date cannot be empty."
        }
        if (toDate.isEmpty()) {
            return "To date cannot be empty."
        }
        if (remark.isEmpty()) {
            return "Remark cannot be empty."
        }
        // Add more validation rules as needed
        return null
    }

}