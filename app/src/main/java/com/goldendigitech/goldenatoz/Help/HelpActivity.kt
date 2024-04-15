package com.goldendigitech.goldenatoz.Help


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.goldendigitech.goldenatoz.MainActivity
import com.goldendigitech.goldenatoz.MyProfile.MyProfile
import com.goldendigitech.goldenatoz.databinding.ActivityHelpBinding

class HelpActivity : AppCompatActivity() {

    private val REQUEST_PHONE_CALL = 1
    private val phoneNumber = "+919769696969"
    lateinit var helpBinding: ActivityHelpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        helpBinding = ActivityHelpBinding.inflate(layoutInflater)
        val view: View = helpBinding.root
        setContentView(view)

        helpBinding.ivBack.setOnClickListener {
            val i = Intent(this@HelpActivity, MainActivity::class.java)
            startActivity(i)
            finishAffinity()
        }

        helpBinding.RelContact.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this@HelpActivity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this@HelpActivity, arrayOf(Manifest.permission.CALL_PHONE), REQUEST_PHONE_CALL)
            } else {
                makePhoneCall()
            }
        }

        helpBinding.RelWhatsapp.setOnClickListener { openWhatsApp(phoneNumber) }

        helpBinding.RelEmail.setOnClickListener { composeEmail() }


        // Set up the back button click listener
        helpBinding.ivBack.setOnClickListener(View.OnClickListener {
            val i: Intent = Intent(this@HelpActivity, MyProfile::class.java)
            startActivity(i)
            finishAffinity()
        })


    }

    private fun makePhoneCall() {
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:$phoneNumber")
        startActivity(callIntent)
    }

    private fun openWhatsApp(number: String) {
        try {
            val uri = Uri.parse("https://api.whatsapp.com/send?phone=$number")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "WhatsApp not installed.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun composeEmail() {
        val addresses = arrayOf("Support@goldigitech.com")
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:" + TextUtils.join(",", addresses))
        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject")
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            val sendIntent = Intent(Intent.ACTION_SEND)
            sendIntent.type = "message/rfc822"
            sendIntent.putExtra(Intent.EXTRA_EMAIL, addresses)
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject")
            if (sendIntent.resolveActivity(packageManager) != null) {
                startActivity(sendIntent)
            } else {
                Toast.makeText(this, "No email app found. Please install an email app to send an email.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PHONE_CALL) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall()
            } else {
                Toast.makeText(this, "Permission Denied for Phone Call", Toast.LENGTH_SHORT).show()
            }
        }
    }

}