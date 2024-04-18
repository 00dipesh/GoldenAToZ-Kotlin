package com.goldendigitech.goldenatoz.attendance

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.hardware.Camera
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.SurfaceHolder
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.goldendigitech.goldenatoz.MainActivity
import com.goldendigitech.goldenatoz.R
import com.goldendigitech.goldenatoz.databinding.ActivityAttendanceBinding
import com.goldendigitech.goldenatoz.employee.Employee
import com.goldendigitech.goldenatoz.employee.EmployeeViewModel
import com.goldendigitech.goldenatoz.singleToneClass.SharedPreferencesManager
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import android.Manifest
import androidx.core.app.ActivityCompat
import android.util.Base64
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import android.location.Geocoder
import android.location.Location
import android.os.Handler
import android.os.Looper
import android.location.Address
import com.goldendigitech.goldenatoz.StateAndCity.StateCityViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener
import java.sql.Types.NULL


class AttendanceActivity : AppCompatActivity() {

    val TAG = "AttendanceActivity"
    lateinit var attendanceBinding: ActivityAttendanceBinding

    private lateinit var attendanceviewModel: AttendanceViewModel
    private lateinit var employeeViewModel: EmployeeViewModel
    private lateinit var taskListViewModel: TaskListViewModel
    private lateinit var stateCityViewModel: StateCityViewModel

    private var selectedWorkType: String = ""
    private var selectedAreaWork: String = ""
    private var address: String = ""
    private var employeecode: String = ""
    private var selectedStatus: String = ""
    private var selectedWorking: String = ""
    private var selectedId: Int? = null
    private var selectedTaskName: String = ""
    lateinit var stateVal: String

    private var camera: android.hardware.Camera? = null
    private var cameraOpened = false
    private lateinit var photo: Bitmap

    private val PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private var handler: Handler? = null
    private var timerRunnable: Runnable? = null
    var latitude = 0.0
    var longitude = 0.0
    var fileContent = ""
    var filetype = "JPG"
    var uniqueFilename: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_attendance)
        attendanceBinding = ActivityAttendanceBinding.inflate(layoutInflater)
        val view: View = attendanceBinding.root
        setContentView(view)

        attendanceviewModel = ViewModelProvider(this).get(AttendanceViewModel::class.java)
        employeeViewModel = ViewModelProvider(this).get(EmployeeViewModel::class.java)
        taskListViewModel = ViewModelProvider(this).get(TaskListViewModel::class.java)
        stateCityViewModel = ViewModelProvider(this).get(StateCityViewModel::class.java)


        // Request camera permission if not granted
        if (checkCameraPermission()) {
            openCamera()
        } else {
            requestCameraPermission()
        }

        // Request location permission if not granted
        if (checkLocationPermission()) {
            lastLocation
        } else {
            requestLocationPermission()
        }

        //refresh location
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        handler = Handler(Looper.getMainLooper())
        timerRunnable = object : Runnable {
            var secondsElapsed = 0
            override fun run() {
                secondsElapsed++
                lastLocation
                handler!!.postDelayed(this, 2000)
            }
        }
        // Start the timer
        handler!!.postDelayed(timerRunnable as Runnable, 1000)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            // Location permission already granted, perform location-related tasks
            lastLocation
        }


        val employeeId = SharedPreferencesManager.getInstance(this).getUserId()
        Log.d("employeeId ID", employeeId.toString())

        // Observe changes in employeeLiveData
        employeeViewModel.employeeLiveData.observe(this, { employee ->
            // Update UI with employee data
            if (employee != null) {
                populateUI(employee)
            }
        })
        // Fetch employee data
        employeeViewModel.getEmployeeData(employeeId)


        //working status spinner
        val status = resources.getStringArray(R.array.working_status)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, status)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        attendanceBinding.spinner.adapter = adapter
        val textColor = ContextCompat.getColor(this, R.color.gryclr)
        attendanceBinding.spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>,
                    selectedItemView: View?,
                    position: Int,
                    id: Long
                ) {
                    // Display a toast with the selected state
                    val textView = (parentView.getChildAt(0) as? TextView)
                    textView?.setTextColor(textColor)
                    selectedStatus = status[position]
                    when (selectedStatus) {
                        "Leave" -> {
                            attendanceBinding.RelLeave.visibility = View.VISIBLE
                            attendanceBinding.layoutStatus.visibility = View.GONE
                        }

                        "Present" -> {
                            attendanceBinding.RelLeave.visibility = View.GONE
                            attendanceBinding.layoutStatus.visibility = View.VISIBLE
                        }

                        else -> {
                            // For other options, hide both layouts
                            attendanceBinding.RelLeave.visibility = View.GONE
                            attendanceBinding.layoutStatus.visibility = View.GONE
                        }
                    }
                }

                override fun onNothingSelected(parentView: AdapterView<*>) {
                    // Do nothing here
                }
            }


        //worktype from spinner
        val workType = resources.getStringArray(R.array.work_type)
        val workAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, workType)
        workAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        attendanceBinding.spinnerWorkType.adapter = workAdapter
        val textColorWork = ContextCompat.getColor(this, R.color.gryclr)
        attendanceBinding.spinnerWorkType.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>,
                    selectedItemView: View?,
                    position: Int,
                    id: Long
                ) {
                    // Display a toast with the selected state
                    val textView = (parentView.getChildAt(0) as? TextView)
                    textView?.setTextColor(textColorWork)
                    selectedWorkType = workType[position]
                }

                override fun onNothingSelected(parentView: AdapterView<*>) {
                    // Do nothing here
                }
            }

        //area_work spinner
        val areaWork = resources.getStringArray(R.array.area_work)
        val areaAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, areaWork)
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        attendanceBinding.spinnerAreaWork.adapter = areaAdapter
        val textColorArea = ContextCompat.getColor(this, R.color.gryclr)
        attendanceBinding.spinnerAreaWork.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>,
                    selectedItemView: View?,
                    position: Int,
                    id: Long
                ) {
                    // Display a toast with the selected state
                    val textView = (parentView.getChildAt(0) as? TextView)
                    textView?.setTextColor(textColorArea)
                    selectedAreaWork = areaWork[position]
                }

                override fun onNothingSelected(parentView: AdapterView<*>) {
                    // Do nothing here
                }
            }

        // spinner working_with
        val working_with = resources.getStringArray(R.array.working_with_array)
        val working_w =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, working_with)
        working_w.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        attendanceBinding.spinnerWorkingWith.adapter = working_w

        val textColor_workingw = ContextCompat.getColor(this, R.color.gryclr)
        attendanceBinding.spinnerWorkingWith.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>,
                    selectedItemView: View?,
                    position: Int,
                    id: Long
                ) {
                    val textView = (parentView.getChildAt(0) as? TextView)
                    textView?.setTextColor(textColor_workingw)
                    selectedWorking = working_with[position] // Use the correct array for comparison

                    // Check the selected item against the array values
                    when (selectedWorking) {
                        "With Senior" -> {
                            attendanceBinding.RelSenior.visibility = View.VISIBLE
                            attendanceBinding.RelJunior.visibility =
                                View.GONE // Hide the other layout
                        }

                        "With Junior" -> {
                            attendanceBinding.RelJunior.visibility = View.VISIBLE
                            attendanceBinding.RelSenior.visibility =
                                View.GONE // Hide the other layout
                        }

                        else -> {
                            // If neither "With Senior" nor "With Junior" is selected, hide both layouts
                            attendanceBinding.RelSenior.visibility = View.GONE
                            attendanceBinding.RelJunior.visibility = View.GONE
                        }
                    }
                }

                override fun onNothingSelected(parentView: AdapterView<*>) {
                    // Do nothing here
                }
            }


        //working with senior & junior spinner api
        attendanceviewModel.handleWorkingWith()
        attendanceviewModel.workingwithsjResponse.observe(this, Observer { workingwith ->
            workingwith?.let {
                if (it.success) {
                    val dataMap: Map<Int, String> = it.data
                    val namesList = dataMap.values.toList()
                    Log.d("namesList", namesList.toString())

                    // Create an ArrayAdapter and set it to the spinnerSenior
                    val adapterSenior =
                        ArrayAdapter(this, android.R.layout.simple_spinner_item, namesList)
                    adapterSenior.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    attendanceBinding.spinnerSenior.adapter = adapterSenior

                    attendanceBinding.spinnerSenior.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>?, view: View?, position: Int, id: Long
                            ) {
                                val selectedName = namesList[position]
                                selectedId = getKeyFromValue(dataMap, selectedName)
                                Log.d("Selected ID", selectedId.toString())
                            }

                            override fun onNothingSelected(p0: AdapterView<*>?) {
                                TODO("Not yet implemented")
                            }
                        }


                    // Create an ArrayAdapter and set it to the spinnerJunior
                    val adapterJunior =
                        ArrayAdapter(this, android.R.layout.simple_spinner_item, namesList)
                    adapterJunior.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    attendanceBinding.spinnerJunior.adapter = adapterJunior

                    attendanceBinding.spinnerJunior.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                val selectedName = namesList[position]
                                selectedId = getKeyFromValue(dataMap, selectedName)
                                Log.d("Selected Junior ID", selectedId.toString())
                            }

                            override fun onNothingSelected(p0: AdapterView<*>?) {
                                TODO("Not yet implemented")
                            }
                        }
                } else {
                    showToast("Internal Server Error")
                }
            }
        })

        //task of the day api
        taskListViewModel.showTaskList()
        taskListViewModel.tasklistResponse.observe(this, Observer { taskResponse ->
            taskResponse?.let {
                if (it.success) {
                    val taskdataMap: Map<String, String> = it.data
                    val taskList = taskdataMap.values.toList()
                    Log.d("namesList", taskList.toString())
                    // Create an ArrayAdapter and set it to the spinnerSenior
                    val adapterSenior =
                        ArrayAdapter(this, android.R.layout.simple_spinner_item, taskList)
                    adapterSenior.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    attendanceBinding.spinnerTask.adapter = adapterSenior

                    attendanceBinding.spinnerTask.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                selectedTaskName = taskList[position]
                                Log.d("Selected ID", selectedId.toString())
                            }

                            override fun onNothingSelected(p0: AdapterView<*>?) {
                                TODO("Not yet implemented")
                            }
                        }


                }
            }

        })


        attendanceBinding.btnSubmit.setOnClickListener {

            val seniorId: Int? = if (selectedWorking == "With Senior") selectedId else null
            val juniorId: Int? = if (selectedWorking == "With Junior") selectedId else null
            // Log the values of seniorId and juniorId
            Log.e("AttendanceActivity", "Selected Attendance Junior: $juniorId")
            Log.e("AttendanceActivity", "Selected Attendance Senior: $seniorId")


            val remark = attendanceBinding.edRemarks.text.toString()
            uniqueFilename = generateUniqueFileName(employeeId.toString())

            captureImage()
            val attendanceRequest = AttendanceModel(
                employeeId,
                employeecode,
                selectedStatus,
                selectedWorkType,
                selectedAreaWork,
                uniqueFilename!!,
                filetype,
                fileContent,
                selectedWorking,
                seniorId, // Default value for seniorId when null
                juniorId, // Default value for juniorId when null
                selectedTaskName,
                latitude,
                longitude,
                remark
            )
            attendanceviewModel.addAttendance(attendanceRequest)
        }

        attendanceviewModel.attendanceResponse.observe(this, Observer { response ->
            if (response != null) {
                Log.e("AttendanceActivity", "Attendance Success: ${response}")

                if (response.success) {
                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finishAffinity()
                } else {

                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                }
            } else {

                Toast.makeText(this, "Null response received", Toast.LENGTH_SHORT).show()
            }
        })

        stateCityViewModel.fetchState()

        stateCityViewModel.statesLiveData.observe(this, Observer { data ->
            if (data != null) {
                val stateNames = ArrayList<String>()
                stateNames.add("Select State")
                for (state in data) {
                    state.StateName?.let { stateNames.add(it) }
                }
                val attendanceadapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, stateNames)
                attendanceadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                attendanceBinding.spinnerState.adapter = attendanceadapter
            }
        })

        attendanceBinding.spinnerState.onItemSelectedListener =
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

    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun populateUI(employee: Employee) {
        attendanceBinding.employeeName.text = "${employee.firstName} ${employee.lastName}"
        attendanceBinding.txtUsername.text = employee.username
        attendanceBinding.txtAddress.text = employee.address
        attendanceBinding.txtEmail.text = employee.email
        attendanceBinding.txtContact.text = employee.contact
        attendanceBinding.txtDesignation.text = employee.designation
        attendanceBinding.txtEmployeecode.text = employee.employeeCode
        Log.e("EmployeeViewModel", "employeeName: ${employee.firstName} \${employee.lastName")
        Log.e("EmployeeViewModel", "username: ${employee.username}")
    }

    private fun getKeyFromValue(map: Map<Int, String>, value: String): Int? {
        for ((key, mapValue) in map) {
            if (mapValue == value) {
                return key
            }
        }
        return null
    }

    //camera code
    private fun checkCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST_CODE
        )
    }

    private fun openCamera() {
        try {
            camera = android.hardware.Camera.open(getFrontCameraId())
            // camera = Camera.open(getFrontCameraId())
            camera?.setDisplayOrientation(90)

            val surfaceHolder = attendanceBinding.surfaceView.holder
            surfaceHolder.addCallback(object : SurfaceHolder.Callback {
                override fun surfaceCreated(holder: SurfaceHolder) {
                    try {
                        camera?.setPreviewDisplay(holder)
                        camera?.startPreview()
                        cameraOpened = true
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }

                override fun surfaceChanged(
                    holder: SurfaceHolder,
                    format: Int,
                    width: Int,
                    height: Int
                ) {
                    if (cameraOpened) {
                        camera?.stopPreview()
                        try {
                            camera?.setPreviewDisplay(holder)
                            camera?.startPreview()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                }

                override fun surfaceDestroyed(holder: SurfaceHolder) {
                    camera?.let {
                        it.stopPreview()
                        it.release()
                        camera = null
                        cameraOpened = false
                    }
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getFrontCameraId(): Int {
        var cameraId = -1
        val numCameras = android.hardware.Camera.getNumberOfCameras()
        for (i in 0 until numCameras) {
            val cameraInfo = android.hardware.Camera.CameraInfo()
            android.hardware.Camera.getCameraInfo(i, cameraInfo)
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                cameraId = i
                break
            }
        }
        return cameraId
    }

    private fun captureImage() {
        camera?.let { camera ->
            try {
                camera.takePicture(null, null, object : android.hardware.Camera.PictureCallback {
                    override fun onPictureTaken(data: ByteArray, camera: android.hardware.Camera) {
                        val pictureFile = getOutputMediaFile()
                        if (pictureFile != null) {
                            try {
                                val fos = FileOutputStream(pictureFile)
                                fos.write(data)
                                fos.close()
                                displayCapturedImage(pictureFile.absolutePath)
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                        }
                        // Restart preview after taking picture
                        camera.startPreview()
                    }
                })
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e(TAG, "Error capturing image: ${e.message}")
                Toast.makeText(this, "Failed to capture image", Toast.LENGTH_SHORT).show()
            }
        } ?: run {
            Log.e(TAG, "Camera is null")
            Toast.makeText(this, "Camera not available", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getOutputMediaFile(): File? {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val mediaStorageDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            "MyCameraApp"
        )

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory")
                return null
            }
        }

        return File(mediaStorageDir.path + File.separator + "IMG_" + timeStamp + ".jpg")
    }

    private fun displayCapturedImage(imagePath: String) {
        runOnUiThread {
            val bitmapimg: Bitmap? = convertImagePathToBitmap(imagePath)
            fileContent = convertBitmapToBase64(bitmapimg)
            Log.d(TAG, "FileContent " + fileContent)
        }
    }

    private fun convertImagePathToBitmap(imagePath: String): Bitmap? {
        return try {
            // Decode the file path into a Bitmap
            BitmapFactory.decodeFile(imagePath)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Location permission granted, start location updates
                lastLocation
            } else {
                Toast.makeText(this, "Location permission required...!!!!", Toast.LENGTH_LONG)
                    .show()
            }
        } else
            if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
                // Handle camera permission request response
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Camera permission granted, open camera
                    openCamera()
                } else {
                    // Camera permission denied, show appropriate message or handle the situation
                    Toast.makeText(this, "Camera permission required...!!!!", Toast.LENGTH_LONG)
                        .show()
                }
            }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            photo = data?.extras?.get("data") as Bitmap
            Log.d(TAG, "Imagebit: $photo")
        }
    }

    private fun convertBitmapToBase64(bitmap: Bitmap?): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    companion object {
        private val TAG = "AttendanceActivity"
        private val LOCATION_PERMISSION_REQUEST_CODE = 101
        private val CAMERA_PERMISSION_REQUEST_CODE = 102
        fun convertImagePathToBitmap(imagePath: String?): Bitmap? {
            try {
                // Decode the file path into a Bitmap
                return BitmapFactory.decodeFile(imagePath)
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
        }

        fun generateUniqueFileName(userId: String): String {
            // Generate a unique filename using timestamp and UUID
            val timestamp =
                SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.getDefault()).format(Date())
            return userId + "_" + timestamp + "_" + UUID.randomUUID().toString()
                .substring(0, 8) + ".jpg"
        }
    }


    // Check for location permission
    private fun checkLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    // Request location permission
    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    private val lastLocation: Unit
        //    public void GetStateApi() {
        private get() {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                fusedLocationProviderClient!!.lastLocation
                    .addOnSuccessListener(this, object : OnSuccessListener<Location?> {
                        override fun onSuccess(location: Location?) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                val geoCoder =
                                    Geocoder(this@AttendanceActivity, Locale.getDefault())
                                val addresses: List<Address>?
                                try {
                                    addresses = geoCoder.getFromLocation(
                                        location.latitude,
                                        location.longitude,
                                        1
                                    )
                                    latitude = addresses!![0].latitude
                                    longitude = addresses[0].longitude
                                    address = addresses[0].getAddressLine(0)
                                    attendanceBinding!!.tvLatitude.text = "Latitude: $latitude"
                                    attendanceBinding!!.tvLongitude.text = "Longitude: $longitude"
                                    attendanceBinding!!.tvGeoAddress.text = "Address: $address"
                                    Log.d(TAG, "Latitude$latitude")
                                    Log.d(TAG, "Latitude$longitude")
                                    Log.d(TAG, "Latitude$address")
                                    showMap(latitude, longitude)
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        }
                    })
            } else {
                askPermission()
            }
        }

    fun showMap(latitude: Double, longitude: Double) {
        val webSettings = attendanceBinding!!.webView.settings
        webSettings.javaScriptEnabled = true
        attendanceBinding!!.webView.canGoBack()
        val url = "https://www.google.com/maps?q=$latitude,$longitude"
        attendanceBinding!!.webView.loadUrl(url)

        // Set WebView clients
        attendanceBinding!!.webView.webViewClient = WebViewClient()
        attendanceBinding!!.webView.webChromeClient = WebChromeClient()
    }

    private fun askPermission() {
        ActivityCompat.requestPermissions(this, PERMISSIONS, LOCATION_PERMISSION_REQUEST_CODE)
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