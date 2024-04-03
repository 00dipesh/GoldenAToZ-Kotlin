package com.goldendigitech.goldenatoz.Idcard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.goldendigitech.goldenatoz.R
import com.goldendigitech.goldenatoz.databinding.ActivityComplaintBinding
import com.goldendigitech.goldenatoz.databinding.ActivityIdCardBinding
import com.goldendigitech.goldenatoz.employee.Employee
import com.goldendigitech.goldenatoz.employee.EmployeeViewModel
import com.goldendigitech.goldenatoz.singleToneClass.SharedPreferencesManager
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class IdCardActivity : AppCompatActivity() {

    lateinit var idCardBinding: ActivityIdCardBinding
    private lateinit var employeeViewModel: EmployeeViewModel
    private var employeeId: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idCardBinding = ActivityIdCardBinding.inflate(layoutInflater)
        val view: View = idCardBinding.root
        setContentView(view)

        employeeViewModel = ViewModelProvider(this).get(EmployeeViewModel::class.java)

         employeeId = SharedPreferencesManager.getInstance(this).getUserId()
        Log.d("employeeId ID", employeeId.toString())

        employeeViewModel.getEmployeeData(employeeId)
        employeeViewModel.employeeLiveData.observe(this, { employee ->
            employee?.let {
                updateUI(employee)
            } ?: run {
                Toast.makeText(this@IdCardActivity, "Failed to get employee details", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun updateUI(employee: Employee) {
        val firstName = employee.firstName
        val middleName = employee.middleName
        val lastName = employee.lastName
        val contact = employee.contact
        val employeeEmail = employee.email
        val dob = employee.dob
        val designation = employee.designation
        val dept = employee.dept
        val bldgrp = employee.bloodGroup

        val inputFormat = SimpleDateFormat("M/d/yyyy h:mm:ss a", Locale.getDefault())
        val outputFormat = SimpleDateFormat("M/d/yyyy", Locale.getDefault())

        idCardBinding.tvDob.text = dob?.let {
            try {
                val date = inputFormat.parse(it)
                outputFormat.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
                "N/A"
            }
        } ?: "N/A"

        val fullName = "${firstName ?: ""} ${middleName ?: ""} ${lastName ?: ""}"
        idCardBinding.tvId.text = employeeId.toString()
        idCardBinding.tvName.text = fullName.trim()
        idCardBinding.tvDesignation.text = designation ?: "N/A"
        idCardBinding.tvDept.text = dept ?: "N/A"
        idCardBinding.tvMbno.text = contact ?: "N/A"
        idCardBinding.tvEmail.text = employeeEmail ?: "N/A"
        idCardBinding.tvBldgrp.text = bldgrp ?: "N/A"
    }
}