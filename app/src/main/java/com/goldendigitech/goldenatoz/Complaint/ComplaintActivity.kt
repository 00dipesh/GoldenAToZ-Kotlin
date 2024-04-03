package com.goldendigitech.goldenatoz.Complaint

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.goldendigitech.goldenatoz.MainActivity
import com.goldendigitech.goldenatoz.R
import com.goldendigitech.goldenatoz.attendance.AttendanceViewModel
import com.goldendigitech.goldenatoz.databinding.ActivityAttendanceBinding
import com.goldendigitech.goldenatoz.databinding.ActivityComplaintBinding
import com.goldendigitech.goldenatoz.singleToneClass.SharedPreferencesManager

class ComplaintActivity : AppCompatActivity() {

    lateinit var complaintBinding: ActivityComplaintBinding
    private lateinit var complaintViewModel: ComplaintViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        complaintBinding = ActivityComplaintBinding.inflate(layoutInflater)
        val view: View = complaintBinding.root
        setContentView(view)

        complaintViewModel = ViewModelProvider(this).get(ComplaintViewModel::class.java)

        val employeeId = SharedPreferencesManager.getInstance(this).getUserId()
        Log.d("employeeId ID", employeeId.toString())

        complaintBinding.btnSubmit.setOnClickListener {
            val complaintName = complaintBinding.edCname.text.toString()
            val complaintType = complaintBinding.edCtype.text.toString()
            val  complaintMessage = complaintBinding.edMessage.text.toString()

            if (validateInput(complaintName, complaintType, complaintMessage)) {
                val complaintModel = ComplaintModel(
                    complaintName,
                    complaintType,
                    employeeId,
                    complaintMessage
                )
                complaintViewModel.addComplaints(complaintModel)
            }
        }

        complaintViewModel.complaintResponse.observe(this, Observer {complaint ->
            if (complaint != null) {
                Log.e("ComplaintActivity", "ComplaintActivity Success: ${complaint}")

                if (complaint.Success) {
                    Toast.makeText(this, complaint.Message, Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finishAffinity()
                } else {

                    Toast.makeText(this, complaint.Message, Toast.LENGTH_SHORT).show()
                }
            } else {

                Toast.makeText(this, "Null response received", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun validateInput(name: String, type: String, message: String): Boolean {
        if (name.isEmpty()) {
            complaintBinding.edCname.error = "Complaint name cannot be empty"
            return false
        }

        if (type.isEmpty()) {
            complaintBinding.edCtype.error = "Complaint type cannot be empty"
            return false
        }

        if (message.isEmpty()) {
            complaintBinding.edMessage.error = "Complaint message cannot be empty"
            return false
        }

        return true
    }
}