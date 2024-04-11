package com.goldendigitech.goldenatoz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.viewpager.widget.ViewPager
import com.goldendigitech.goldenatoz.Adapter.TabLayoutAdapter
import com.goldendigitech.goldenatoz.MyProfile.MyProfile
import com.goldendigitech.goldenatoz.employee.Employee
import com.goldendigitech.goldenatoz.employee.EmployeeViewModel
import com.goldendigitech.goldenatoz.login.LoginScreen
import com.goldendigitech.goldenatoz.login.LogoutViewModel
import com.goldendigitech.goldenatoz.serverConnect.Constant
import com.goldendigitech.goldenatoz.singleToneClass.SharedPreferencesManager
import com.google.android.material.bottomnavigation.BottomNavigationView

import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import retrofit2.Call

class MainActivity : AppCompatActivity(), View.OnClickListener {

    val TAG = "MainActivity"
    lateinit var toolbar: Toolbar
    lateinit var drawer: DrawerLayout
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var rel_nav_myprofile: RelativeLayout
    lateinit var rel_nav_getretailers: RelativeLayout
    lateinit var rel_nav_holiday: RelativeLayout
    lateinit var rel_nav_leave: RelativeLayout
    lateinit var rel_nav_updatedata: RelativeLayout
    lateinit var rel_nav_syncdata: RelativeLayout
    lateinit var rel_nav_sharedata: RelativeLayout
    lateinit var rel_nav_datadump: RelativeLayout
    lateinit var rel_nav_logout: RelativeLayout
    lateinit var fab: FloatingActionButton
    lateinit var tv_name: TextView
    lateinit var tv_email: TextView
    lateinit var tv_contactno: TextView
    lateinit var tv_lastsync: TextView
    lateinit var tv_version: TextView
    lateinit var iv_userprofile: ImageView
    lateinit var logoutButton: ImageView
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    private lateinit var employeeViewModel: EmployeeViewModel
    var employeeId: Int = 0
    lateinit var email: String
    lateinit var contact: String
    lateinit var logoutViewModel: LogoutViewModel
    var employeeEmail: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //setSupportActionBar()
        employeeViewModel = ViewModelProvider(this).get(EmployeeViewModel::class.java)
        logoutViewModel = ViewModelProvider(this).get(LogoutViewModel::class.java)


        employeeId = SharedPreferencesManager.getInstance(this).getUserId()

        drawer = findViewById(R.id.drawer)
        rel_nav_myprofile = findViewById(R.id.rel_nav_myprofile)
        rel_nav_getretailers = findViewById(R.id.rel_nav_getretailers)
        rel_nav_holiday = findViewById(R.id.rel_nav_holiday)
        rel_nav_leave = findViewById(R.id.rel_nav_leave)
        rel_nav_updatedata = findViewById(R.id.rel_nav_updatedata)
        rel_nav_syncdata = findViewById(R.id.rel_nav_syncdata)
        rel_nav_sharedata = findViewById(R.id.rel_nav_sharedata)
        rel_nav_datadump = findViewById(R.id.rel_nav_datadump)
        rel_nav_logout = findViewById(R.id.rel_nav_logout)
        toolbar = findViewById(R.id.toolbar)
        tabLayout = findViewById(R.id.tablayout)
        viewPager = findViewById(R.id.viewPager)
        fab = findViewById(R.id.fab)
        tv_name = findViewById(R.id.tv_name)
        tv_email = findViewById(R.id.tv_email)
        tv_contactno = findViewById(R.id.tv_contactno)
        tv_lastsync = findViewById(R.id.tv_lastsync)
        tv_version = findViewById(R.id.tv_version)
        iv_userprofile = findViewById(R.id.iv_userprofile)
        logoutButton = findViewById(R.id.logoutButton)
        bottomNavigationView = findViewById(R.id.bottom_nav)

        employeeViewModel.getEmployeeData(employeeId)
        employeeViewModel.employeeLiveData.observe(this, { employee ->
            employee?.let {
                updateUI(employee)
            } ?: run {
                Toast.makeText(
                    this@MainActivity,
                    "Failed to get employee details",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })


        rel_nav_myprofile!!.setOnClickListener(this)
        rel_nav_getretailers!!.setOnClickListener(this)
        rel_nav_updatedata!!.setOnClickListener(this)
        rel_nav_syncdata!!.setOnClickListener(this)
        rel_nav_sharedata!!.setOnClickListener(this)
        rel_nav_datadump!!.setOnClickListener(this)
        rel_nav_logout!!.setOnClickListener(this)
        rel_nav_holiday!!.setOnClickListener(this)
        rel_nav_leave!!.setOnClickListener(this)

        tabLayout.addTab(tabLayout.newTab().setText("Performance"))
        tabLayout.addTab(tabLayout.newTab().setText("Product View"))
        tabLayout.addTab(tabLayout.newTab().setText("Analysis"))
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL)
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.tabindicator))
        val adapter = TabLayoutAdapter(this, supportFragmentManager, tabLayout.tabCount)
        viewPager.setAdapter(adapter)
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.setCurrentItem(tab.position)
                //                tab.view.setBackgroundResource(tabindicator);
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
//                tab.view.setBackgroundResource(android.R.color.transparent);
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                // Do nothing on tab reselected
            }
        })

        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close)
        drawer!!.addDrawerListener(toggle)
        toggle.drawerArrowDrawable.color = resources.getColor(R.color.drawermenu)
        toggle.syncState()


        bottomNavigationView.setOnNavigationItemSelectedListener(nav)
        fab.setOnClickListener(View.OnClickListener { view -> showPopupMenu(view) })

        logoutButton.setOnClickListener(View.OnClickListener {
            employeeEmail?.let { it1 -> logoutViewModel.doLogoutUser(it1, contact) }

            SharedPreferencesManager.getInstance(this).clearUserData()
            val intent = Intent(this, LoginScreen::class.java)
            startActivity(intent)
            finish()
        })

        logoutViewModel.logoutResponce.observe(this, Observer { response ->
            if (response != null) {
                if (response.success) {
//                    SharedPreferencesManager.getInstance(this).clearUserData()
//                    val intent = Intent(this, LoginScreen::class.java)
//                    startActivity(intent)
//                    finish()
                    showToast("Logout Successful")
                    Log.d("TAG", "This is a debug response" + response.message)

                } else {
                    showToast("Logout Failed: ${response.message}")
                }
            }

        })


    }


    val nav = BottomNavigationView.OnNavigationItemSelectedListener { menuItems ->
        when (menuItems.itemId) {
            R.id.bottom_home -> {
                Toast.makeText(this@MainActivity, "Home", Toast.LENGTH_LONG).show()
                true
            }

            R.id.bottom_me -> {
                Toast.makeText(this@MainActivity, "Me", Toast.LENGTH_LONG).show()
                true
            }

            else -> false
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.rel_nav_myprofile -> {
                // Handle click for My Profile
                val intent = Intent(this@MainActivity, MyProfile::class.java)
                startActivity(intent)
            }

            R.id.rel_nav_getretailers -> {
                // Handle click for Get Retailers
                Toast.makeText(this@MainActivity, "This is Get Retailers", Toast.LENGTH_LONG).show()
            }

            R.id.rel_nav_updatedata -> {
                // Handle click for Update Data
                Toast.makeText(this@MainActivity, "This is Update data", Toast.LENGTH_LONG).show()
            }

            R.id.rel_nav_holiday -> {
                // Handle click for Holiday
                //val intent = Intent(this@MainActivity, HolidayCalenderActivity::class.java)
                //startActivity(intent)
            }

            R.id.rel_nav_leave -> {
                // Handle click for Leave
                //val intent = Intent(this@MainActivity, LeaveStatusActivity::class.java)
                //startActivity(intent)
            }

            R.id.rel_nav_syncdata -> {
                // Handle click for Sync Data
                Toast.makeText(this@MainActivity, "This is Sync Data", Toast.LENGTH_LONG).show()
            }

            R.id.rel_nav_sharedata -> {
                // Handle click for Share Data
                Toast.makeText(this@MainActivity, "This is Share Data", Toast.LENGTH_LONG).show()
            }

            R.id.rel_nav_logout -> {
                // Handle click for Logout
                // doLogoutUser()
            }
        }
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(this@MainActivity, view)
        popupMenu.inflate(R.menu.float_menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_today ->                         // Handle Option 1 click
                    true

                R.id.menu_yesterday ->                         // Handle Option 2 click
                    true

                R.id.menu_lastweek ->                         // Handle Option 3 click
                    true

                R.id.menu_thismonth ->                         // Handle Option 4 click
                    true

                R.id.menu_lastmonth ->                         // Handle Option 5 click
                    true

                R.id.menu_customdate ->                         // Handle Option 5 click
                    true

                else -> false
            }
        }
        popupMenu.show()
    }

    private fun updateUI(employee: Employee) {

        val firstName = employee.firstName
        val middleName = employee.middleName
        val lastName = employee.lastName
        employeeEmail = employee.email
        contact = employee.contact
        val lastSyncDate = employee.lastSyncDate
        val appVersion = employee.appVersion

        tv_name!!.text = "$firstName $middleName $lastName"
        tv_email!!.text = employeeEmail
        tv_contactno!!.text = contact
        tv_lastsync!!.text = lastSyncDate
        tv_version!!.text = appVersion
    }

    private fun showToast(message: String) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
    }
}