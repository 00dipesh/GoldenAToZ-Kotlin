package com.goldendigitech.goldenatoz.Leave

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.goldendigitech.goldenatoz.Leave.Response.LeaveViewModel
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
            datePickerDialog.datePicker.maxDate = cal.timeInMillis
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
        val empcode = employee.employeeCode

        val fullName = "$firstName $middleName $lastName"
        leaveApplicationBinding.edName.text = Editable.Factory.getInstance().newEditable(fullName)
        leaveApplicationBinding.edEmpid.text = Editable.Factory.getInstance().newEditable(empcode)
    }

}