package com.goldendigitech.goldenatoz.UpdatePersonalInfo

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.goldendigitech.goldenatoz.MyProfile.MyProfile
import com.goldendigitech.goldenatoz.R
import com.goldendigitech.goldenatoz.databinding.ActivityUpdatePersonalInfoBinding
import com.goldendigitech.goldenatoz.employee.Employee
import com.goldendigitech.goldenatoz.employee.EmployeeViewModel
import com.goldendigitech.goldenatoz.singleToneClass.SharedPreferencesManager
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class UpdatePersonalInfo : AppCompatActivity() {

    lateinit var updatePersonalInfoBinding: ActivityUpdatePersonalInfoBinding
    lateinit var employeeViewModel: EmployeeViewModel
    lateinit var updatePersonalInfoViewModel: UpdatePersonalInfoViewModel
    private var CalculatedAge: Int? = null
    var mStatus: String? = null
    var gender: String? = null
    var bldgrp: String? = null
    private val genderList = arrayOf("Select Gender", "Male", "Female", "Transgender")
    private lateinit var maritalStatusList: Array<String>
    private lateinit var bloodGroupList: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updatePersonalInfoBinding = ActivityUpdatePersonalInfoBinding.inflate(layoutInflater)
        val view: View = updatePersonalInfoBinding.root
        setContentView(view)

        employeeViewModel = ViewModelProvider(this).get(EmployeeViewModel::class.java)
        updatePersonalInfoViewModel = ViewModelProvider(this).get(UpdatePersonalInfoViewModel::class.java)

        val employeeId = SharedPreferencesManager.getInstance(this).getUserId()
        Log.d("employeeId ID", employeeId.toString())
        maritalStatusList = resources.getStringArray(R.array.maritualstatus)
        bloodGroupList = resources.getStringArray(R.array.bloodgroup)


        // Observe changes in employeeLiveData
        employeeViewModel.employeeLiveData.observe(this) { employee ->
            // Update UI with employee data
            if (employee != null) {
                populateUI(employee)
            }
        }
        // Fetch employee data
        employeeViewModel.getEmployeeData(employeeId)


        //DATE OF BIRTH
        updatePersonalInfoBinding.edDob.setOnClickListener {
            val cal = Calendar.getInstance()
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)
            val dialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { datePicker, year, monthOfYear, dayOfMonth ->
                    val selectedMonth = monthOfYear + 1 // Incremented value for month
                    val selectedDate = "$dayOfMonth/$selectedMonth/$year"
                    updatePersonalInfoBinding.edDob.setText(selectedDate)
                    calculateAndDisplayAge(year, selectedMonth, dayOfMonth)
                },
                year,
                month,
                day
            )
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
            dialog.show()
        }

        //DATE OF JOINING
        updatePersonalInfoBinding.edDoj.setOnClickListener {
            val cal = Calendar.getInstance()
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)
            val dialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { datePicker, selectedYear, selectedMonth, selectedDay ->
                    val incrementedMonth = selectedMonth + 1 // Incremented value for month
                    val selectedDate = "$selectedDay/$incrementedMonth/$selectedYear"
                    updatePersonalInfoBinding.edDoj.setText(selectedDate)
                },
                year,
                month,
                day
            )
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
            dialog.show()
        }


        // Spinner code for gender
        val genderAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genderList)
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        updatePersonalInfoBinding.edGender.adapter = genderAdapter
        updatePersonalInfoBinding.edGender.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    // Handle item selection
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {
                    // Handle no selection
                }
            }

        // Spinner code for marital status
        val maritalStatusAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, maritalStatusList)
        maritalStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        updatePersonalInfoBinding.edMaritualstatus.adapter = maritalStatusAdapter
        updatePersonalInfoBinding.edMaritualstatus.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedItem = maritalStatusList[position]
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {
                    // Handle no selection
                }
            }

        // Spinner code for blood group
        val bloodGroupAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, bloodGroupList)
        bloodGroupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        updatePersonalInfoBinding.edBloodgroup.adapter = bloodGroupAdapter
        updatePersonalInfoBinding.edBloodgroup.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedItem = bloodGroupList[position]
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {
                    // Handle no selection
                }
            }


        updatePersonalInfoBinding.chSameasaddress.setOnCheckedChangeListener { buttonView, isChecked ->
            // If CheckBox is checked, set Address to be the same as PermanentAddress
            if (isChecked) {
                updatePersonalInfoBinding.edPaddress.setText(updatePersonalInfoBinding.edAddress.text.toString())
                updatePersonalInfoBinding.edPaddress.isEnabled = true // Disable editing when same as PermanentAddress
            } else {
                updatePersonalInfoBinding.edAddress.text.clear() // Clear Address when CheckBox is unchecked
                updatePersonalInfoBinding.edAddress.isEnabled = true // Enable editing
            }
        }

      updatePersonalInfoBinding.tvSave.setOnClickListener {
          val firstNameVal = updatePersonalInfoBinding.edFname.text.toString().trim()
          val middleNameVal = updatePersonalInfoBinding.edMname.text.toString().trim()
          val lastNameVal = updatePersonalInfoBinding.edLname.text.toString().trim()
          val phoneVal = updatePersonalInfoBinding.edPhone.text.toString().trim()
          val alternateNo = updatePersonalInfoBinding.edAlternatephone.text.toString().trim()
          val dobVal = updatePersonalInfoBinding.edDob.text.toString().trim()
          val genderVal = updatePersonalInfoBinding.edGender.selectedItem.toString().trim()
          val addressVal = updatePersonalInfoBinding.edAddress.text.toString().trim()
          val pAddressVal = updatePersonalInfoBinding.edPaddress.text.toString().trim()
          val mStatusVal = updatePersonalInfoBinding.edMaritualstatus.selectedItem.toString().trim()
          val qualificationVal = updatePersonalInfoBinding.edQualification.text.toString().trim()
          val skillVal = updatePersonalInfoBinding.edSkill.text.toString().trim()
          val workExperienceVal = updatePersonalInfoBinding.edWrkexperience.text.toString().trim()
          val dojVal = updatePersonalInfoBinding.edDoj.text.toString().trim()
          val bloodGroupVal = updatePersonalInfoBinding.edBloodgroup.selectedItem.toString().trim()
          val deptVal = updatePersonalInfoBinding.edDept.text.toString().trim()
          val designationVal = updatePersonalInfoBinding.edDesignation.text.toString().trim()

          val workExperience: Int = workExperienceVal.toIntOrNull() ?: 0

          val validationMessage = validateFields(firstNameVal, middleNameVal, lastNameVal,phoneVal,alternateNo,dobVal, genderVal,addressVal,pAddressVal,mStatusVal,qualificationVal,skillVal,workExperienceVal,dojVal,bloodGroupVal,deptVal,designationVal)
          if (validationMessage != null) {
              Toast.makeText(this, validationMessage, Toast.LENGTH_SHORT).show() //error message
          } else {
              // Validation successful, proceed with password update
              val request = UpdatePersonalInfoModel(firstNameVal, middleNameVal, lastNameVal,phoneVal,alternateNo,dobVal,CalculatedAge,genderVal,bloodGroupVal ,addressVal,pAddressVal,mStatusVal,qualificationVal,skillVal,workExperience,dojVal,deptVal,designationVal,employeeId)
              updatePersonalInfoViewModel.updateUserPersonalInfo(request)
          }
      }

        //observer
       updatePersonalInfoViewModel.updatePersonalInfoResponse.observe(this, Observer { updateInfo ->
           if (updateInfo != null) {
               Log.e("UpdatePersonalInfo", "UpdatePersonalInfo Success: ${updateInfo}")
               if (updateInfo.success) {
                   Toast.makeText(this, updateInfo.message, Toast.LENGTH_SHORT).show()
                   val intent = Intent(this, MyProfile::class.java)
                   startActivity(intent)
                   finishAffinity()
               } else {

                   Toast.makeText(this, updateInfo.message, Toast.LENGTH_SHORT).show()
               }
           } else {

               Toast.makeText(this, "Null response received", Toast.LENGTH_SHORT).show()
           }

       })

    }

    private fun populateUI(employee: Employee?) {
        employee?.let { employee ->
            with(updatePersonalInfoBinding) {
                edFname.setText(employee.firstName)
                edMname.setText(employee.middleName)
                edLname.setText(employee.lastName)
                edPhone.setText(employee.contact)
                edAlternatephone.setText(employee.alternateContact)
                edDob.setText(formatDate(employee.dob))
                edAddress.setText(employee.address)
                edPaddress.setText(employee.permanentAddress)
                edQualification.setText(employee.qualification)
                edSkill.setText(employee.skill)
                edDoj.setText(formatDate(employee.joiningDate))
                edDept.setText(employee.dept)
                edDesignation.setText(employee.designation)
                edWrkexperience.setText(employee.workExperience.toString())
                mStatus = employee.maritalStatus
                gender = employee.gender
                bldgrp = employee.bloodGroup

                mStatus?.let { status ->
                    val position = maritalStatusList.indexOf(status)
                    if (position != -1) {
                        updatePersonalInfoBinding.edMaritualstatus.setSelection(position)
                    }
                }

                gender?.let { gen ->
                    val position = genderList.indexOf(gen)
                    if (position != -1) {
                        updatePersonalInfoBinding.edGender.setSelection(position)
                    }
                }

                bldgrp?.let { blood ->
                    val position = bloodGroupList.indexOf(blood)
                    if (position != -1) {
                        updatePersonalInfoBinding.edBloodgroup.setSelection(position)
                    }
                }

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
    private fun calculateAndDisplayAge(birthYear: Int, birthMonth: Int, birthDay: Int) {
        val today = Calendar.getInstance()
        val currentYear = today.get(Calendar.YEAR)
        val currentMonth = today.get(Calendar.MONTH) + 1 // Increment by 1 as months are zero-based
        val currentDay = today.get(Calendar.DAY_OF_MONTH)

        CalculatedAge = currentYear - birthYear

        if (currentMonth < birthMonth || (currentMonth == birthMonth && currentDay < birthDay)) {
            CalculatedAge = CalculatedAge?.minus(1)
        }
    }

    private fun validateFields(
        firstName: String,
        middleName: String,
        lastName: String,
        phone: String,
        alternateNo: String,
        dob: String,
        gender: String,
        address: String,
        pAddress: String,
        mStatus: String,
        qualification: String,
        skill: String,
        workExperience: String,
        doj: String,
        bldgrp: String,
        dept: String,
        designation: String
    ): String? {
        // Perform validation checks
        if (firstName.isEmpty()) {
            return "Please Enter First Name"
        }
        if (middleName.isEmpty()) {
            return "Please Enter Middle Name"
        }
        if (lastName.isEmpty()) {
            return "Please Enter Last Name"
        }
        if (phone.isEmpty()) {
            return "Please Enter Mobile Number"
        }
        if (alternateNo.isEmpty()) {
            return "Please Enter Alternate Mobile Number"
        }
        if (dob.isEmpty()) {
            return "Select Date of Birth"
        }
        if (gender.isEmpty()) {
            // Handle gender validation
            // Add your logic or remove this condition if not needed
        }
        if (address.isEmpty()) {
            return "Please Enter Address"
        }
        if (pAddress.isEmpty()) {
            return "Please Enter Permanent Address"
        }
        if (mStatus.isEmpty()) {
            // Handle marital status validation
            // Add your logic or remove this condition if not needed
            return "Select Marital Status"
        }
        if (qualification.isEmpty()) {
            return "Please Enter Qualification"
        }
        if (skill.isEmpty()) {
            return "Please Enter Skill"
        }
        if (workExperience.isEmpty()) {
            return "Please Enter Work Experience"
        }
        if (doj.isEmpty()) {
            return "Select Date of Joining"
        }
        if (bldgrp.isEmpty()) {
            return "Select Blood Group"
        }
        if (dept.isEmpty()) {
            return "Please Enter Department"
        }
        if (designation.isEmpty()) {
            return "Please Enter Designation"
        }
        // Add more validation rules as needed
        return null
    }


}