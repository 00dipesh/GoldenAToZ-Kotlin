package com.goldendigitech.goldenatoz.Visitingcard

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.goldendigitech.goldenatoz.MyProfile.MyProfile
import com.goldendigitech.goldenatoz.databinding.ActivityVisitingCardBinding
import com.goldendigitech.goldenatoz.employee.Employee
import com.goldendigitech.goldenatoz.employee.EmployeeViewModel
import com.goldendigitech.goldenatoz.singleToneClass.SharedPreferencesManager
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class VisitingCard : AppCompatActivity() {

    lateinit var visitingCardBinding: ActivityVisitingCardBinding
    private lateinit var employeeViewModel: EmployeeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        visitingCardBinding = ActivityVisitingCardBinding.inflate(layoutInflater)
        val view: View = visitingCardBinding.root
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

        visitingCardBinding.ivShare.setOnClickListener {
            handleShareVisitingCard()
        }

        visitingCardBinding.ivBack.setOnClickListener {
            val i = Intent(this@VisitingCard, MyProfile::class.java)
            startActivity(i)
            finishAffinity()
        }

    }

    private fun populateUI(employee: Employee) {
        val firstName = employee.firstName
        val middleName = employee.middleName
        val lastName = employee.lastName
        val employeeEmail = employee.email
        val contact = employee.contact
        val designation = employee.designation

        // Concatenate first name, middle name, and last name using let function
        val fullName = buildString {
            firstName?.let { append(it) }
            middleName?.let { append(" $it") }
            lastName?.let { append(" $it") }
        }

        // Update your UI elements here
        visitingCardBinding.tvName.text = fullName
        visitingCardBinding.tvDesignation.text = designation ?: "N/A"
        visitingCardBinding.tvEmail.text = employeeEmail ?: "N/A"
        visitingCardBinding.tvPhone.text = contact ?: "N/A"
    }


    private fun handleShareVisitingCard() {
        val bitmap = captureView(visitingCardBinding.cvMain)
        val imagePath = saveBitmap(bitmap)
        shareImage(imagePath)
    }

    private fun captureView(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    private fun saveBitmap(bitmap: Bitmap): File {
        val imagePath = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "screenshot.png")
        try {
            val fos = FileOutputStream(imagePath)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos.flush()
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return imagePath
    }
    private fun shareImage(imagePath: File) {
        val contentUri = FileProvider.getUriForFile(
            this,
            "com.goldendigitech.goldenatoz.provider",
            imagePath
        )
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "image/*"
        shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri)
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Visiting Card")
        startActivity(Intent.createChooser(shareIntent, "Share Visiting Card"))
    }

}