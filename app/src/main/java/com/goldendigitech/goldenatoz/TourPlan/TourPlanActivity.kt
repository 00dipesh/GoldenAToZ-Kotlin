package com.goldendigitech.goldenatoz.TourPlan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.goldendigitech.goldenatoz.R
import com.goldendigitech.goldenatoz.StateAndCity.StateCityViewModel
import com.goldendigitech.goldenatoz.TourPlan.TourPlanModel.AddTourModel
import com.goldendigitech.goldenatoz.TourPlan.TourPlanModel.AddTourResponse
import com.goldendigitech.goldenatoz.TourPlan.TourPlanModel.DistributorData
import com.goldendigitech.goldenatoz.TourPlan.TourPlanModel.GetBeatResponce
import com.goldendigitech.goldenatoz.TourPlan.TourPlanModel.GetDistributorResponce
import com.goldendigitech.goldenatoz.TourPlan.TourPlanModel.GetSSResponce
import com.goldendigitech.goldenatoz.TourPlan.TourPlanModel.GetTaskResponce
import com.goldendigitech.goldenatoz.TourPlan.TourPlanModel.GetTownResponce
import com.goldendigitech.goldenatoz.TourPlan.TourPlanModel.SSData
import com.goldendigitech.goldenatoz.databinding.ActivitySelectTourPlanBinding
import com.goldendigitech.goldenatoz.databinding.ActivityTourPlanBinding
import com.goldendigitech.goldenatoz.serverConnect.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TourPlanActivity : AppCompatActivity() {

    val TAG = "TourPlanActivity"
    lateinit var tourPlanBinding: ActivityTourPlanBinding
    lateinit var stateCityViewModel: StateCityViewModel
    lateinit var TownName: MutableList<String>
    lateinit var SSName: MutableList<String>
    lateinit var distributorNames: MutableList<String>
    lateinit var BeatName: MutableList<String>
    lateinit var dateVal: String
    lateinit var dayVal: String
    lateinit var taskdayVal: String
    lateinit var stateVal: String
    lateinit var cityVal: String
    lateinit var ssVal: String
    lateinit var townVal: String
    lateinit var beatVal: String
    lateinit var distributorVal: String


// private var TownName: MutableList<String>? = null
// private var SSName: MutableList<String>? = null
// private var distributorNames: MutableList<String>? = null
//    private var BeatName: MutableList<String>? = null
//    private var dateVal: String? = null
//    private var dayVal: String? = null
//    private var taskdayVal: String? = null
//    private var stateVal: String? = null
//    private var cityVal: String? = null
//    private var ssVal: String? = null
//    private var townVal: String? = null
//    private var beatVal: String? = null
//    private var distributorVal: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_tour_plan)
        tourPlanBinding = ActivityTourPlanBinding.inflate(layoutInflater)
        val view: View = tourPlanBinding.root
        setContentView(view)

        stateCityViewModel = ViewModelProvider(this).get(StateCityViewModel::class.java)

        Log.d("TAG", "response citiesm "+stateCityViewModel)
        val intent = intent
        val selectedDate = intent.getStringExtra("selected_date")
        val selectedDay = intent.getStringExtra("selected_day")

        tourPlanBinding.edDate.text = Editable.Factory.getInstance().newEditable(selectedDate ?: "")
        tourPlanBinding.edDay.text = Editable.Factory.getInstance().newEditable(selectedDay ?: "")

        stateCityViewModel.fetchState()

        stateCityViewModel.statesLiveData.observe(this, Observer { data ->
            if (data != null) {
                val stateNames = ArrayList<String>()
                stateNames.add("Select State")
                for (state in data) {
                    state.StateName?.let { stateNames.add(it) }
                }
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, stateNames)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                tourPlanBinding.spinnerState.adapter = adapter
            }
        })

        tourPlanBinding.spinnerState.onItemSelectedListener =
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

        stateCityViewModel.citiesLiveData.observe(this, Observer { cities ->
            if (cities != null) {
                val cityAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cities)
                cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                tourPlanBinding.spinnerCity.adapter = cityAdapter
                Log.d("TAG", "response : "+cityAdapter)
                Log.d("TAG", "response cities "+cities)
            }
        })

        GetTaskList()
        getSSList()
        GetTownList()
        handleDistributorList()
        getBeatName()

        tourPlanBinding.spinnerTask.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                taskdayVal = adapterView?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }

        tourPlanBinding.spinnerSsname.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                ssVal = adapterView?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }

        tourPlanBinding.spinnerTown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                townVal = adapterView?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }

        tourPlanBinding.spinnerDistributor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                distributorVal = adapterView?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }

        tourPlanBinding.spinnerBeat.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                beatVal = adapterView?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }

        tourPlanBinding.submitBtn.setOnClickListener {
            handleSubmitClick()
        }

    }

    private fun handleSubmitClick() {
        handleAddTourPlan()
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

    private fun GetTaskList() {

        val taskModelCall: Call<GetTaskResponce> = Constant.webService.GET_TASK_MODEL_CALL()
        taskModelCall.enqueue(object : Callback<GetTaskResponce> {
            override fun onResponse(call: Call<GetTaskResponce>, response: Response<GetTaskResponce>) {
                if (response.code() == 200) {
                    Log.d(TAG, "tasklist res: " + response.code())
                    val taskModel = response.body()
                    val userData: Map<Int, String>? = taskModel?.data
                    val userList = ArrayList(userData?.values)
                    userList.add(0, "Select Task")
                    val taskadapter = ArrayAdapter(this@TourPlanActivity, android.R.layout.simple_spinner_dropdown_item, userList)
                    taskadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    tourPlanBinding.spinnerTask.adapter = taskadapter
                    Log.d(TAG, "tasklist: $taskModel")
                } else {
                    showToast("Internal Server Error..!!!")
                }
            }

            override fun onFailure(call: Call<GetTaskResponce>, t: Throwable) {
                showToast("Network Error")
            }
        })
    }

    fun getSSList() {

        val ssModelCall = Constant.webService.SS_MODEL_CALL();
        ssModelCall.enqueue(object :Callback<GetSSResponce> {
            override fun onResponse(call: Call<GetSSResponce>, response: Response<GetSSResponce>) {
                if (response.code() == 200) {
                    Log.d(TAG, "ssmodel res: " + response.code())
                    val ssmodel = response.body()
                    val SSList: List<SSData> = ssmodel?.Data ?: emptyList()
                    val SSName = ArrayList<String>()
                    SSName.add("Select SS")
                    Log.d(TAG, "ssmodel res: " + SSName)
                    for (ssData in SSList) {
                        ssData.Name?.let { SSName.add(it) }
                    }
                    val SSAdapter =ArrayAdapter(this@TourPlanActivity,
                        android.R.layout.simple_dropdown_item_1line,SSName)
                    SSAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    tourPlanBinding.spinnerSsname.adapter = SSAdapter
                }
                else {
                    showToast("Internal Server Error..!!!")
                }
            }

            override fun onFailure(call: Call<GetSSResponce>, t: Throwable) {
                showToast(t.message!!)
            }

        })
    }

    private fun GetTownList() {
        val townModelCall: Call<GetTownResponce> = Constant.webService.TOWN_MODEL_CALL()
        townModelCall.enqueue(object : Callback<GetTownResponce> {
            override fun onResponse(call: Call<GetTownResponce>, response: Response<GetTownResponce>) {
                if (response.code() == 200) {
                    Log.d(TAG, "townmodel res: " + response.code())
                    val getTownModel = response.body()
                    val TownList = getTownModel?.Data
                    TownName = ArrayList()
                    TownName?.add("Select Town")
                    Log.d(TAG, "townmodel res: $TownName")
                    TownList?.let {
                        for (townData in it) {
                            townData.Name?.let { it1 -> (TownName as ArrayList<String>)?.add(it1) }
                        }
                    }
                    val townAdapter = ArrayAdapter(this@TourPlanActivity, android.R.layout.simple_dropdown_item_1line, TownName!!)
                    townAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    tourPlanBinding.spinnerTown.adapter = townAdapter
                } else {
                    showToast("Internal Server Error")
                }
            }

            override fun onFailure(call: Call<GetTownResponce>, t: Throwable) {
                showToast(t.message!!)
            }
        })
    }

    private fun handleAddTourPlan() {
        val dateVal = tourPlanBinding.edDate.text.toString().trim()
        val dayVal = tourPlanBinding.edDay.text.toString().trim()
        val model = AddTourModel().apply {
            Date = dateVal
            Day = dayVal
            TaskOfDay = taskdayVal
            State = stateVal
            SSName = mutableListOf(ssVal)
            DistributorName = distributorVal
            BeatName = beatVal
            Town = townVal
        }

        val responceCall =Constant.webService.addTourPlan(model)
        responceCall.enqueue(object : Callback<AddTourResponse> {
            override fun onResponse(
                call: Call<AddTourResponse>,
                response: Response<AddTourResponse>
            ) {
                Log.d(TAG, "response code ${response.code()}")
                if (response.code() == 200) {
                    val tourResponse = response.body()
                    if (tourResponse?.Success == true) {
                        tourResponse.Message?.let { showToast(it) }
                        val intent = Intent(this@TourPlanActivity, SelectTourPlanActivity::class.java)
                        startActivity(intent)
                        finishAffinity()
                    } else {
                        showToast(tourResponse?.Message ?: "Unknown Error")
                    }
                } else {
                    showToast("Internal Server Error")
                }
            }

            override fun onFailure(call: Call<AddTourResponse>, t: Throwable) {
                showToast("Network Failure")
            }

        })
    }

    private fun handleDistributorList() {
        val distributorCall:Call<GetDistributorResponce> = Constant.webService.getDistributorList()
        distributorCall.enqueue(object :Callback<GetDistributorResponce> {
            override fun onResponse(
                call: Call<GetDistributorResponce>,
                response: Response<GetDistributorResponce>
            ) {
                if (response.isSuccessful) {
                    val responseDistributor: GetDistributorResponce? = response.body()
                    if (responseDistributor != null) {
                        val distributorNames = ArrayList<String>()
                        val distributorList: List<DistributorData> = responseDistributor.Data
                        distributorNames.add("Select Distributor")
                        for (distributor in distributorList) {
                            distributor.Name?.let { distributorNames.add(it) }
                        }
                        val distributorAdapter = ArrayAdapter(this@TourPlanActivity, android.R.layout.simple_dropdown_item_1line, distributorNames)
                        distributorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        tourPlanBinding.spinnerDistributor.adapter = distributorAdapter
                    }
                }            }

            override fun onFailure(call: Call<GetDistributorResponce>, t: Throwable) {
                showToast(t.message!!)
            }

        })
    }
    fun getBeatName() {

        val beatModelCall =Constant.webService.BEAT_MODEL_CALL()
        beatModelCall.enqueue(object :Callback<GetBeatResponce> {
            override fun onResponse(
                call: Call<GetBeatResponce>,
                response: Response<GetBeatResponce>
            ) {
                if (response.code() == 200) {
                    val beatModel = response.body()
                    val beatList = beatModel?.Data ?: emptyList()
                    val beatName = ArrayList<String>()
                    Log.d(TAG, "townmodel res: $beatName")
                    for (beatData in beatList) {
                        beatData.Name?.let { beatName.add(it) }
                    }
                    beatName.add("Select Beat Name")
                    val beatAdapter = ArrayAdapter(this@TourPlanActivity, android.R.layout.simple_dropdown_item_1line, beatName)
                    beatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    tourPlanBinding.spinnerBeat.adapter = beatAdapter
                } else {
                    Toast.makeText(this@TourPlanActivity, "Internal Server Error..!!!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GetBeatResponce>, t: Throwable) {
                showToast(t.message!!)
            }

        })
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}