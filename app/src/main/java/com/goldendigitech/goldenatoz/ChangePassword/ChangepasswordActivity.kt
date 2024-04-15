package com.goldendigitech.goldenatoz.ChangePassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.goldendigitech.goldenatoz.MainActivity
import com.goldendigitech.goldenatoz.MyProfile.MyProfile
import com.goldendigitech.goldenatoz.databinding.ActivityChangepasswordBinding
import com.goldendigitech.goldenatoz.singleToneClass.SharedPreferencesManager

class ChangepasswordActivity : AppCompatActivity() {

    lateinit var changepasswordBinding: ActivityChangepasswordBinding
    private lateinit var changepasswordViewModel: ChangepasswordViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changepasswordBinding = ActivityChangepasswordBinding.inflate(layoutInflater)
        val view: View = changepasswordBinding.root
        setContentView(view)

        changepasswordViewModel = ViewModelProvider(this).get(ChangepasswordViewModel::class.java)

        val employeeId = SharedPreferencesManager.getInstance(this).getUserId()
        val email = SharedPreferencesManager.getInstance(this).getEmail()
        Log.d("employeeId ID", employeeId.toString())

        changepasswordBinding.btnSubmit.setOnClickListener {
            val oldpswd = changepasswordBinding.edOldpswd.text.toString()
            val newpswd = changepasswordBinding.edNewpswd.text.toString()
            val cnfpswd = changepasswordBinding.edNewpswd.text.toString()

            val validationMessage = validateFields(oldpswd, newpswd, cnfpswd)
            if (validationMessage != null) {
                // Display validation error message

                Toast.makeText(this, validationMessage, Toast.LENGTH_SHORT).show()

            } else {
                // Validation successful, proceed with password update
                val request = ChangepasswordModel(email, oldpswd, newpswd, cnfpswd)
                changepasswordViewModel.updatePassword(request)
            }
        }

        changepasswordViewModel.changepasswordResponse.observe(this, Observer { changepswd ->
            if (changepswd != null) {
                Log.e("ChangepasswordActivity", "ChangepasswordActivity Success: ${changepswd}")

                if (changepswd.success) {
                    Toast.makeText(this, changepswd.message, Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finishAffinity()
                } else {

                    Toast.makeText(this, changepswd.message, Toast.LENGTH_SHORT).show()
                }
            } else {

                Toast.makeText(this, "Null response received", Toast.LENGTH_SHORT).show()
            }

        })

        // Set up the back button click listener
        changepasswordBinding.ivBack.setOnClickListener(View.OnClickListener {
            val i: Intent = Intent(this@ChangepasswordActivity, MyProfile::class.java)
            startActivity(i)
            finishAffinity()
        })


    }


    private fun validateFields(
        oldPassword: String,
        newPassword: String,
        confirmPassword: String
    ): String? {
        // Perform validation checks
        if (oldPassword.isEmpty()) {
            return "Old password cannot be empty."
        }
        if (newPassword.isEmpty()) {
            return "New password cannot be empty."
        }
        if (confirmPassword.isEmpty()) {
            return "Confirm password cannot be empty."
        }
        if (newPassword != confirmPassword) {
            return "New password and confirm password do not match."
        }
        // Add more validation rules as needed
        return null
    }
}