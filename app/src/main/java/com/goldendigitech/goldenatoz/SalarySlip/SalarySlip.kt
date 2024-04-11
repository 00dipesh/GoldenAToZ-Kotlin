package com.goldendigitech.goldenatoz.SalarySlip

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.goldendigitech.goldenatoz.MyProfile.MyProfile
import com.goldendigitech.goldenatoz.databinding.ActivitySalarySlipBinding

class SalarySlip : AppCompatActivity() {

    val TAG = "SalarySlip"
    lateinit var salarySlipBinding: ActivitySalarySlipBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_salary_slip)
        salarySlipBinding = ActivitySalarySlipBinding.inflate(layoutInflater)
        val view: View = salarySlipBinding.root
        setContentView(view)

        salarySlipBinding!!.ivBack.setOnClickListener {
            val ivback = Intent(this@SalarySlip,MyProfile ::class.java)
            startActivity(ivback)
        }



    }
}