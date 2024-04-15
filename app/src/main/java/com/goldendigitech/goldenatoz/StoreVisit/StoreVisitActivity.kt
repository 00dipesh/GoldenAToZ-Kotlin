package com.goldendigitech.goldenatoz.StoreVisit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.goldendigitech.goldenatoz.MainActivity
import com.goldendigitech.goldenatoz.R
import com.goldendigitech.goldenatoz.databinding.ActivityComplaintBinding
import com.goldendigitech.goldenatoz.databinding.ActivityStoreVisitBinding

class StoreVisitActivity : AppCompatActivity() {

    lateinit var storeVisitBinding: ActivityStoreVisitBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storeVisitBinding = ActivityStoreVisitBinding.inflate(layoutInflater)
        val view: View = storeVisitBinding.root
        setContentView(view)


        storeVisitBinding.arroBack.setOnClickListener {
            val intent = Intent(this@StoreVisitActivity,MainActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }



    }
}