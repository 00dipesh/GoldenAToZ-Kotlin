package com.goldendigitech.goldenatoz.Feedback


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.goldendigitech.goldenatoz.MainActivity
import com.goldendigitech.goldenatoz.databinding.ActivityFeedbackFormBinding
import com.goldendigitech.goldenatoz.singleToneClass.SharedPreferencesManager

class FeedbackForm : AppCompatActivity() {

    lateinit var feedbackFormBinding: ActivityFeedbackFormBinding
    lateinit var feedbackViewModel: FeedbackViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        feedbackFormBinding = ActivityFeedbackFormBinding.inflate(layoutInflater)
        val view: View = feedbackFormBinding.root
        setContentView(view)

        feedbackViewModel = ViewModelProvider(this).get(FeedbackViewModel::class.java)

        val employeeId = SharedPreferencesManager.getInstance(this).getUserId()
        Log.d("employeeId ID", employeeId.toString())


        feedbackFormBinding.btnSubmit.setOnClickListener {
            val feedbackname = feedbackFormBinding.edEname.text.toString()
            val feedbacktitle = feedbackFormBinding.edTitle.text.toString()
            val feedbackmessages = feedbackFormBinding.edMessage.text.toString()

            if (validateInput(feedbackname, feedbacktitle, feedbackmessages)) {
                val feedbackModel = FeedbackModel(
                    feedbackname,
                    feedbacktitle,
                    employeeId,
                    feedbackmessages
                )
                feedbackViewModel.addFeedback(feedbackModel)
            }
    }

        feedbackViewModel.feedbackResponse.observe(this, Observer {feedback ->
            if (feedback != null) {
                Log.e("ComplaintActivity", "ComplaintActivity Success: ${feedback}")

                if (feedback.Success) {
                    Toast.makeText(this, feedback.Message, Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finishAffinity()
                } else {

                    Toast.makeText(this, feedback.Message, Toast.LENGTH_SHORT).show()
                }
            } else {

                Toast.makeText(this, "Null response received", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun validateInput(name: String, title: String, message: String): Boolean {
        if (name.isEmpty()) {
            feedbackFormBinding.edEname.error = "Feedback name cannot be empty"
            return false
        }

        if (title.isEmpty()) {
            feedbackFormBinding.edTitle.error = "Feedback title cannot be empty"
            return false
        }

        if (message.isEmpty()) {
            feedbackFormBinding.edMessage.error = "Feedback message cannot be empty"
            return false
        }

        return true

    }
}