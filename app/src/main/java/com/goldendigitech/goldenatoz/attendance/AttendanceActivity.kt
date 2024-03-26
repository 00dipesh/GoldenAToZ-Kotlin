package com.goldendigitech.goldenatoz.attendance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.goldendigitech.goldenatoz.R

class AttendanceActivity : AppCompatActivity() {

    private val viewModel: AttendanceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attendance)

    }
}