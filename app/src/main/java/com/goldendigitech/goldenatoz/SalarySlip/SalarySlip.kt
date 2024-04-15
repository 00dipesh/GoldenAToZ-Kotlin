package com.goldendigitech.goldenatoz.SalarySlip

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.goldendigitech.goldenatoz.singleToneClass.SharedPreferencesManager


import android.view.View
import com.goldendigitech.goldenatoz.MyProfile.MyProfile
import com.goldendigitech.goldenatoz.databinding.ActivitySalarySlipBinding
import android.util.Log



class SalarySlip : AppCompatActivity() {

    private lateinit var salarySlipBinding: ActivitySalarySlipBinding
    lateinit var salarySlipViewModel: SalarySlipViewModel
    private lateinit var adapter: SalarySlipAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        salarySlipBinding = ActivitySalarySlipBinding.inflate(layoutInflater)
        val view: View = salarySlipBinding.root
        setContentView(view)


        salarySlipBinding!!.ivBack.setOnClickListener {
            val ivback = Intent(this@SalarySlip,MyProfile ::class.java)
            startActivity(ivback)
        }


        salarySlipViewModel = ViewModelProvider(this).get(SalarySlipViewModel::class.java)

        val employeeId = SharedPreferencesManager.getInstance(this).getUserId()
        Log.d("employeeId ID", employeeId.toString())

        adapter = SalarySlipAdapter(emptyList()) // Initially empty list
        salarySlipBinding.rvSalaryslip.adapter = adapter
        salarySlipBinding.rvSalaryslip.layoutManager = LinearLayoutManager(this)

        salarySlipViewModel.salarySlipResponse.observe(this,{salarySlips->
            salarySlips?.let {
                adapter.salarySlips = it
                adapter.notifyDataSetChanged()
                if (it.isEmpty()) {
                    salarySlipBinding.tvNodata.visibility = View.VISIBLE
                } else {
                    salarySlipBinding.tvNodata.visibility = View.GONE
                }
            }
        })





    }
}