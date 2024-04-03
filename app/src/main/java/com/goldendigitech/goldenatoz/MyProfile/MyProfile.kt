package com.goldendigitech.goldenatoz.MyProfile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.goldendigitech.goldenatoz.ChangePassword.ChangepasswordActivity
import com.goldendigitech.goldenatoz.Complaint.ComplaintActivity
import com.goldendigitech.goldenatoz.Feedback.FeedbackForm
import com.goldendigitech.goldenatoz.Help.HelpActivity
import com.goldendigitech.goldenatoz.Idcard.IdCardActivity
import com.goldendigitech.goldenatoz.PersonalInfo.PersonalInfo
import com.goldendigitech.goldenatoz.R
import com.goldendigitech.goldenatoz.SalarySlip.SalarySlip
import com.goldendigitech.goldenatoz.Visitingcard.VisitingCard
import com.goldendigitech.goldenatoz.databinding.ActivityMyProfileBinding
import com.goldendigitech.goldenatoz.employee.Employee
import com.goldendigitech.goldenatoz.employee.EmployeeViewModel
import com.goldendigitech.goldenatoz.singleToneClass.SharedPreferencesManager

class MyProfile : AppCompatActivity(), View.OnClickListener
{

    lateinit var myProfileBinding: ActivityMyProfileBinding
    private lateinit var employeeViewModel: EmployeeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myProfileBinding = ActivityMyProfileBinding.inflate(layoutInflater)
        val view: View = myProfileBinding.root
        setContentView(view)

        employeeViewModel = ViewModelProvider(this).get(EmployeeViewModel::class.java)

        val employeeId = SharedPreferencesManager.getInstance(this).getUserId()
        Log.d("employeeId ID", employeeId.toString())

        // Observe changes in employeeLiveData
        employeeViewModel.employeeLiveData.observe(this, { employee ->
            // Update UI with employee data
            if (employee != null) {
                populateUI(employee)
            }
        })
        // Fetch employee data
        employeeViewModel.getEmployeeData(employeeId)



        myProfileBinding.tvPersonalinfo.setOnClickListener(this)
        myProfileBinding.tvIdcard.setOnClickListener(this)
        myProfileBinding.tvVisitingcard.setOnClickListener(this)
        myProfileBinding.tvChangepswd.setOnClickListener(this)
        myProfileBinding.tvSalaryslip.setOnClickListener(this)
        myProfileBinding.tvFeedback.setOnClickListener(this)
        myProfileBinding.tvComplaint.setOnClickListener(this)
        myProfileBinding.tvHelpdesk.setOnClickListener(this)

    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.tv_personalinfo -> startActivity(Intent(this, PersonalInfo::class.java))
            R.id.tv_idcard -> startActivity(Intent(this, IdCardActivity::class.java))
            R.id.tv_visitingcard -> startActivity(Intent(this, VisitingCard::class.java))
            R.id.tv_changepswd -> startActivity(Intent(this, ChangepasswordActivity::class.java))
            R.id.tv_salaryslip -> startActivity(Intent(this, SalarySlip::class.java))
            R.id.tv_feedback -> startActivity(Intent(this, FeedbackForm::class.java))
            R.id.tv_helpdesk -> startActivity(Intent(this, HelpActivity::class.java))
            R.id.tv_complaint -> startActivity(Intent(this, ComplaintActivity::class.java))
            else -> {
            }
        }
    }

    private fun populateUI(employee: Employee) {
        val firstName = employee.firstName
        val middleName = employee.middleName
        val lastName = employee.lastName
        val fullName = "$firstName $middleName $lastName".trim()
        myProfileBinding.tvName.text = fullName
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}