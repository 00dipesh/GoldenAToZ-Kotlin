package com.goldendigitech.goldenatoz.Leave

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.goldendigitech.goldenatoz.Leave.Adapter.ViewPagerAdapter
import com.goldendigitech.goldenatoz.MainActivity
import com.goldendigitech.goldenatoz.databinding.ActivityLeaveStatusBinding
import com.goldendigitech.goldenatoz.employee.DocumentViewModel
import com.goldendigitech.goldenatoz.employee.Employee
import com.goldendigitech.goldenatoz.employee.EmployeeViewModel
import com.goldendigitech.goldenatoz.singleToneClass.SharedPreferencesManager

class LeaveStatus : AppCompatActivity() {

    private lateinit var leaveStatusBinding: ActivityLeaveStatusBinding
    private lateinit var employeeViewModel: EmployeeViewModel
    private lateinit var documentViewModel: DocumentViewModel
    private lateinit var viewPagerAdapter: ViewPagerAdapter // Declare viewPagerAdapter as a property

    var employeeId: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        leaveStatusBinding = ActivityLeaveStatusBinding.inflate(layoutInflater)
        val view: View = leaveStatusBinding.root
        setContentView(view)

        employeeViewModel = ViewModelProvider(this).get(EmployeeViewModel::class.java)
        documentViewModel = ViewModelProvider(this).get(DocumentViewModel::class.java)

        employeeId = SharedPreferencesManager.getInstance(this).getUserId()


        employeeViewModel.getEmployeeData(employeeId)
        employeeViewModel.employeeLiveData.observe(this, { employee ->
            employee?.let {
                updateUI(employee)
            } ?: run {
                Toast.makeText(
                    this@LeaveStatus,
                    "Failed to get employee details",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        documentViewModel.documentLiveData.observe(this,{documents ->
            documents?.let {
                // Assuming you want to display the first photo in the list
                if (documents.isNotEmpty()) {
                    val firstPhoto = documents.firstOrNull { it.fileName == "Photo" } // Assuming "Photo" is the fileName of the photo
                    firstPhoto?.let {
                        // Decode the Base64 string and set it to ImageView
                        val decodedBytes = Base64.decode(it.fileContent, Base64.DEFAULT)
                        val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
                        leaveStatusBinding.ivProfile.setImageBitmap(bitmap)
                    }
                } else {
                    // Handle case where there are no documents
                }
            }
        })

        documentViewModel.getDocumentsByEmployeeId(employeeId)

        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        leaveStatusBinding.viewPager.adapter = viewPagerAdapter

        leaveStatusBinding.tabsLeaves.setupWithViewPager(leaveStatusBinding.viewPager)

        leaveStatusBinding.fab.setOnClickListener { view ->
            val intent = Intent(this@LeaveStatus, LeaveApplication::class.java)
            startActivity(intent)
        }

        leaveStatusBinding.ivBack.setOnClickListener { view ->
            val intent = Intent(this@LeaveStatus, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun updateUI(employee: Employee) {
        val firstName = employee.firstName
        val middleName = employee.middleName
        val lastName = employee.lastName
        val designation = employee.designation
        leaveStatusBinding.tvName!!.text = "$firstName $middleName $lastName"
        leaveStatusBinding.tvDesignation!!.text = designation

    }
}