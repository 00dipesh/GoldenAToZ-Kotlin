package com.goldendigitech.goldenatoz.SalarySlip

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import android.util.Base64
import com.goldendigitech.goldenatoz.databinding.ActivitySalarySlipViewBinding

class SalarySlipView : AppCompatActivity() {

    private lateinit var salarySlipViewBinding: ActivitySalarySlipViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        salarySlipViewBinding = ActivitySalarySlipViewBinding.inflate(layoutInflater)
        val view: View = salarySlipViewBinding.root
        setContentView(view)

        val pdffile = intent.getStringExtra("PDFFILE")
        val filename = intent.getStringExtra("FILENAME")

        if (pdffile != null && filename != null) {
            salarySlipViewBinding.tvPdfname.text = formatFileName(filename)

            val dummyData  = "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HKoj48KEXwZU9zzTWdiIw7ZfmR3miGSAuCceXG/M8XPNA9+2udpmZGydzdxqxq13ttiBCMW5XwZVImonIQ5rvnRkqEouLKpjhTvhjK2shpEh63cDWNoNyJgwvIhAfaNeKW5CQWgHWRWDTX+Omn4Byr5D646GFDAoMWYEBq6jGlWkFKeEFzoyDmIqcbnxTwR5/y4QltvUkuGLPyfTOuuvKdblnNGRERobug3JVqAhIxPgypwaMnI9yUpmqlGzBkSPyKCmgLtRttNo/pj6BKqpnqXVH74nIzCnfiAjD4OdZun0NSq/JGNlilsyVOCY8pQLG/8NGZabWEdkHOfo1pORw2G9kgcnw5UrX/uNwTZJUiFRedDdyabHYryZMrKxfCRjjtc22ZnAiIwWMCfV2KyLenTv5Y4rzcPYE4QMW64gasz5CmThnSRQGFyAHkcVExen2IYNnbLrQpaQAbk3nAnUUYFvyID5bk0C1fSmEVfZE/rKgAxMHm7GLAxrhQQcC967TCb82Uw958Hm01VAiYll+qk6tgzIcFkKq7K+Smso+vcPZEheTST6vy8Dm2MyzoXZF/WJDLhoWp6m8kDsNf1Nk4FMm5JoA0Q73eIlQWm6FPfgmwTa9YY/dqCNkWoNBv3lJW+GBas9+VTSdL7MKzNux0/HzGWmn5M923HF2P6qmiSn3L8iQ7RNLSUDtJfUB7+2ncBkqtZyY67uy9VMfo4CFhBoq4m2oebkOZAbWhSsQpoD9luG6vfBFnEfXP8ifs7FVSSOzElAEjPE5uXJMDZ0IZ2nxb8fyn0gAw9X5mbvfh1q3UZkYF5QN/t2C7q6hGW6aeEeAey2TPPQPHKXnKU+qPpACbnHB34QBpSeKNdJtcDPSetVVB4H9nUV2iikpz9SPAPkH3ecLL1Yf9Z8DqBDCyvuFDw9sRPfY6HDk/dIRTo3rzuWFn23K/dDLXggs4/8x7YXL168+AL/AGhYgzwKZW5kc3RyZWFtCmVuZG9iagp4cmVmCjAgMTAKMDAwMDAwMDAwMCA2NTUzNSBmIAowMDAwMDAyNzE2IDAwMDAwIG4gCjAwMDAwMDI5NDkgMDAwMDAgbiAKMDAwMDAwMjQ4MyAwMDAwMCBuIAowMDAwMDAyMzE5IDAwMDAwIG4gCjAwMDAwMDAwMTUgMDAwMDAgbiAKMDAwMDAwMjc3MCAwMDAwMCBuIAowMDAwMDAyODYwIDAwMDAwIG4gCjAwMDAwMDMwMDAgMDAwMDAgbiAKMDAwMDAwMzkyNSAwMDAwMCBuIAp0cmFpbGVyCjw8L0lEIFs8NzhiODllZWJmYzVmMDM0NjEyZTAzMmY0ZTVhMjg5NjZhNDdhNTY4OTA1NDgxMGIwZjcyMjQ2MzBhMGExZDc4ZWMzZWIyNWUyNmRkYzY2MmE1N2M2OTQ5OWNlZGI4MjBjMTJlOGVkMTA2ZTkzYTRiYWY3MWE2M2UzMDYwZWEwYWY+PDc4Yjg5ZWViZmM1ZjAzNDYxMmUwMzJmNGU1YTI4OTY2YTQ3YTU2ODkwNTQ4MTBiMGY3MjI0NjMwYTBhMWQ3OGVjM2ViMjVlMjZkZGM2NjJhNTdjNjk0OTljZWRiODIwYzEyZThlZDEwNmU5M2E0YmFmNzFhNjNlMzA2MGVhMGFmPl0vSW5mbyAzIDAgUi9Sb290IDEgMCBSL1NpemUgMTA+PgolaVRleHQtcGRmSFRNTC01LjAuMgpzdGFydHhyZWYKNTI1MgolJUVPRgo="

            val decodedBytes = Base64.decode(dummyData, Base64.DEFAULT)

            if (decodedBytes.isNotEmpty()) {
                try {
                    salarySlipViewBinding.viewSalaryslip.fromBytes(decodedBytes)
                        .defaultPage(0)
                        .enableSwipe(true)
                        .swipeHorizontal(false)
                        .enableDoubletap(true)
                        .load()
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e("PDF Loading", "Error loading PDF: ${e.message}")
                    Toast.makeText(this, "Error loading PDF", Toast.LENGTH_SHORT).show()
                }
            } else {
                Log.e("PDF Loading", "Decoded byte array is null or empty")
                Toast.makeText(this, "PDF data is empty", Toast.LENGTH_SHORT).show()
            }
        } else {
            Log.e("PDF Loading", "PDF file or filename is null")
            Toast.makeText(this, "PDF file or filename is null", Toast.LENGTH_SHORT).show()
        }

        salarySlipViewBinding.ivBack.setOnClickListener {
            val iback = Intent(this, SalarySlip::class.java)
            startActivity(iback)
            finishAffinity()
        }
    }

    private fun formatFileName(fileName: String): String {
        val nameWithoutExtension = fileName.substring(0, fileName.lastIndexOf(".")) // Remove the file extension
        val formattedName = nameWithoutExtension.replace("_", " ") // Replace underscores with spaces
        val sb = StringBuilder()
        for (word in formattedName.split(" ")) { // Capitalize the first letter of each word
            sb.append(Character.toUpperCase(word[0])).append(word.substring(1)).append(" ")
        }
        return sb.toString().trim() // Trim the extra space at the end
    }
}