package com.goldendigitech.goldenatoz.PersonalInfo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.goldendigitech.goldenatoz.UpdatePersonalInfo.UpdatePersonalInfo
import com.goldendigitech.goldenatoz.databinding.ActivityPersonalInfoBinding
import com.goldendigitech.goldenatoz.employee.DocumentViewModel
import com.goldendigitech.goldenatoz.employee.Employee
import com.goldendigitech.goldenatoz.employee.EmployeeViewModel
import com.goldendigitech.goldenatoz.singleToneClass.SharedPreferencesManager
import java.text.SimpleDateFormat
import java.util.Locale

class PersonalInfo : AppCompatActivity() {
    private lateinit var personalInfoBinding: ActivityPersonalInfoBinding
    lateinit var employeeViewModel: EmployeeViewModel
    lateinit var documentViewModel: DocumentViewModel
    private lateinit var documentAdapter: DocumentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        personalInfoBinding = ActivityPersonalInfoBinding.inflate(layoutInflater)
        val view: View = personalInfoBinding.root
        setContentView(view)

        employeeViewModel = ViewModelProvider(this).get(EmployeeViewModel::class.java)
        documentViewModel = ViewModelProvider(this).get(DocumentViewModel::class.java)

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


       // Initially, pass an empty list
        documentAdapter = DocumentAdapter(this)
        personalInfoBinding.rvDocuments.layoutManager = LinearLayoutManager(this)
        personalInfoBinding.rvDocuments.adapter = documentAdapter
        documentViewModel.documentLiveData.observe(this,{documents ->
            documents?.let {
                // Update RecyclerView adapter with new list of documents
                documentAdapter.updateData(documents)
            }
        })

        documentViewModel.getDocumentsByEmployeeId(employeeId)

        personalInfoBinding.ivEdit.setOnClickListener { view ->
            val intent = Intent(this@PersonalInfo, UpdatePersonalInfo::class.java)
            startActivity(intent)
            finishAffinity()
        }


    }

    private fun populateUI(employee: Employee?) {
        employee?.let { employee ->
            with(personalInfoBinding) {
                tvFname.text = employee.firstName
                tvMname.text = employee.middleName ?: ""
                tvLname.text = employee.lastName
                tvPhone.text = employee.contact
                tvAlternatephone.text = employee.alternateContact
                tvDob.text = formatDate(employee.dob)
                tvGender.text = employee.gender
                tvBloodgroup.text = employee.bloodGroup
                tvAddress.text = employee.address
                tvPaddress.text = employee.permanentAddress
                tvMaritualstatus.text = employee.maritalStatus
                tvQualification.text = employee.qualification
                tvSkill.text = employee.skill
                tvWrkexperience.text = employee.workExperience.toString()
                tvDoj.text = formatDate(employee.joiningDate)
                tvDept.text = employee.dept
                tvDesignation.text = employee.designation
            }
        }
    }

    private fun formatDate(dateString: String?): String {
        if (dateString.isNullOrEmpty()) return "" // Handle null or empty date strings

        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())

        return try {
            val date = inputFormat.parse(dateString)
            outputFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            "" // Handle parsing exceptions
        }
    }

}