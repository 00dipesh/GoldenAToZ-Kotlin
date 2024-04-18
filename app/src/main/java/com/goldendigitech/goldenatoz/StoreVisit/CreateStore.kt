package com.goldendigitech.goldenatoz.StoreVisit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.goldendigitech.goldenatoz.MainActivity
import com.goldendigitech.goldenatoz.R
import com.goldendigitech.goldenatoz.StateAndCity.StateCityViewModel
import com.goldendigitech.goldenatoz.databinding.ActivityCreateStoreBinding

class CreateStore : AppCompatActivity() {

    val TAG = "CreateStore"
    lateinit var createStorebinding: ActivityCreateStoreBinding
    lateinit var stateCityViewModel: StateCityViewModel
    lateinit var stateVal: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createStorebinding = ActivityCreateStoreBinding.inflate(layoutInflater)
        val view: View = createStorebinding.root
        setContentView(view)

        stateCityViewModel = ViewModelProvider(this).get(StateCityViewModel::class.java)
        stateCityViewModel.fetchState()


        createStorebinding.arroBack.setOnClickListener {
            val intent = Intent(this@CreateStore, MainActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }

        stateCityViewModel.statesLiveData.observe(this) { data ->
            if (data != null) {
                val stateName = ArrayList<String>()
                stateName.add("Select State")
                for (state in data) {
                    state.StateName?.let { stateName.add(it) }
                }
                val stateAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, stateName)
                stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                createStorebinding.spinnerSvState.adapter = stateAdapter
            }
        }

        createStorebinding.spinnerSvState.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position > 0) {
                        stateVal = parent?.getItemAtPosition(position).toString()
                        val stateId = getStateIdByName(stateVal)
                        stateCityViewModel.fetchCity(stateId)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Handle nothing selected
                }
            }

        stateCityViewModel.citiesLiveData.observe(this) { cities ->
            if (cities != null) {
                val cityAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cities)
                cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                createStorebinding.spinnerSvCity.adapter = cityAdapter
                Log.d("TAG", "response : " + cityAdapter)
                Log.d("TAG", "response cities " + cities)
            }
        }

        val storetype = resources.getStringArray(R.array.store_type)
        val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,storetype)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        createStorebinding.spinnerSvType.adapter =adapter

        createStorebinding.btnSvCreate.setOnClickListener {
           // val i = Intent
            val i=Intent(this@CreateStore,MainActivity::class.java)
            startActivity(i)
            
        }
    }


    private fun getStateIdByName(stateName: String): Int {
        val states = stateCityViewModel.statesLiveData.value
        states?.let {
            for (state in it) {
                if (state.StateName == stateName) {
                    return state.Id!!
                }
            }
        }
        return -1
    }



}