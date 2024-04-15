package com.goldendigitech.goldenatoz.SalarySlip

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.goldendigitech.goldenatoz.MyProfile.MyProfile
import com.goldendigitech.goldenatoz.databinding.ActivitySalarySlipBinding
import com.goldendigitech.goldenatoz.singleToneClass.SharedPreferencesManager


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


        adapter = SalarySlipAdapter(this) // Replace YourAdapterName with your actual adapter name
        salarySlipBinding.rvSalaryslip.adapter = adapter
        salarySlipBinding.rvSalaryslip.layoutManager = LinearLayoutManager(this)

        // Observe LiveData in ViewModel
        salarySlipViewModel.salarySlipResponse.observe(this, Observer { salarySlipList ->
            salarySlipList?.let {
                // Update RecyclerView adapter with new data
                if (salarySlipList.isEmpty()) {
                    // Show empty view if the list is empty
                    salarySlipBinding.tvNodata.visibility = View.VISIBLE
                } else {
                    // Hide empty view if the list is not empty
                    salarySlipBinding.tvNodata.visibility = View.GONE
                    // Update RecyclerView adapter with new data
                    adapter.updateData(salarySlipList)
                }
            }
        })

        salarySlipViewModel.showSalarySlip(employeeId)


        // Set up the back button click listener
        salarySlipBinding.ivBack.setOnClickListener(View.OnClickListener {
            val i: Intent = Intent(this@SalarySlip, MyProfile::class.java)
            startActivity(i)
            finishAffinity()
        })

    }
}