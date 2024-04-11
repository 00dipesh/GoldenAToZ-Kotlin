package com.goldendigitech.goldenatoz.Performance

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.goldendigitech.goldenatoz.Home.HomeAdapter
import com.goldendigitech.goldenatoz.Home.HomeModel
import com.goldendigitech.goldenatoz.Home.HomeSubMenuAdapter
import com.goldendigitech.goldenatoz.Home.HomeSubMenuModel
import com.goldendigitech.goldenatoz.R
import com.goldendigitech.goldenatoz.TourPlan.SelectTourPlanActivity
import com.goldendigitech.goldenatoz.employee.Employee
import com.goldendigitech.goldenatoz.employee.EmployeeViewModel
import com.goldendigitech.goldenatoz.singleToneClass.SharedPreferencesManager
import com.mikhaellopez.circularimageview.CircularImageView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PerformanceFragment : Fragment(), HomeAdapter.OnItemClickListener {


    val TAG = "PerformanceFragment"
    lateinit var rv_homemenu: RecyclerView
    lateinit var rv_homesubmenu: RecyclerView
    lateinit var iv_userprofile: CircularImageView
    lateinit var homeAdapter: HomeAdapter
    lateinit var homeSubMenuAdapter: HomeSubMenuAdapter
    lateinit var list: List<HomeModel>
    lateinit var hlist: List<HomeSubMenuModel>
    lateinit var indicatorLayout: LinearLayout
    lateinit var tv_marquee: TextView
    lateinit var tv_date: TextView
    lateinit var tv_uname: TextView

    lateinit var employeeViewModel:EmployeeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = LayoutInflater.from(container?.context)
            .inflate(R.layout.fragment_performance, container, false)

        employeeViewModel = ViewModelProvider(this).get(EmployeeViewModel::class.java)

        subMenuList()
        getHomeMenuList()
        rv_homemenu = view.findViewById(R.id.rv_homemenu)
        rv_homesubmenu = view.findViewById(R.id.rv_homesubmenu)
        iv_userprofile = view.findViewById(R.id.iv_userprofile)
        tv_marquee = view.findViewById(R.id.tv_marquee)
        tv_date = view.findViewById(R.id.tv_date)
        tv_uname = view.findViewById(R.id.tv_uname)
        indicatorLayout = view.findViewById(R.id.indicatorsLayout)

        val employeeId = SharedPreferencesManager.getInstance(requireContext()).getUserId()
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        tv_date.setText(dateFormat.format(currentDate))

        employeeViewModel.employeeLiveData.observe(viewLifecycleOwner, { employee ->
            // Update UI with employee data
            if (employee != null) {
                populateUI(employee)
            }else{
                Toast.makeText(context, "Failed to get employee details", Toast.LENGTH_SHORT).show()
            }
        })
        employeeViewModel.getEmployeeData(employeeId)
        Log.d("Selected  ID", employeeId.toString())
// Fetch employee data

        rv_homemenu.setHasFixedSize(true)
        val layoutManager = GridLayoutManager(requireContext(), 3)
        layoutManager.orientation = RecyclerView.VERTICAL
        rv_homemenu.layoutManager = layoutManager
        homeAdapter = HomeAdapter(requireContext(), list, this)
        rv_homemenu.adapter = homeAdapter

// Home sub menu
        homeSubMenuAdapter = HomeSubMenuAdapter(requireContext(), hlist)
        rv_homesubmenu.adapter = homeSubMenuAdapter
        rv_homesubmenu.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        for (i in hlist.indices) {
            val indicator = View(context)
            indicator.setBackgroundResource(R.drawable.indicator_zero) // Create a drawable for the indicator
            val layoutParams = LinearLayout.LayoutParams(
                20, // Width of each indicator
                20 // Height of each indicator
            )
            layoutParams.setMargins(8, 0, 8, 0) // Adjust margin as needed
            indicator.layoutParams = layoutParams
            indicatorLayout.addView(indicator)
        }

        rv_homesubmenu.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                updateIndicators()
            }
        })

        return view
    }



    private fun subMenuList() {

        hlist = listOf(
            HomeSubMenuModel("Teamsize", "4"),
            HomeSubMenuModel("Working", "12"),
            HomeSubMenuModel("Attendance", "3"),
            HomeSubMenuModel("Leave", "2"),
            HomeSubMenuModel("Total Outlet", "50"),
            HomeSubMenuModel("New Outlet", "10"),
            HomeSubMenuModel("Outlet Cover", "-"),
            HomeSubMenuModel("Beat Cover", "-"),
            HomeSubMenuModel("Total Call", "8"),
            HomeSubMenuModel("P call", "0"),
            HomeSubMenuModel("sec.tgt.value ", "1.0"),
            HomeSubMenuModel("Sale Qty", "1.0"),
            HomeSubMenuModel("Pri Target", "5"),
            HomeSubMenuModel("Distributor", "6"),
            HomeSubMenuModel("Pri Sale Qty", "6"),
            HomeSubMenuModel("No of Sku", "110")
        )
    }

    private fun getHomeMenuList() {
        list = listOf(
            HomeModel(R.drawable.iv_activityform, "Activity Form"),
            HomeModel(R.drawable.iv_approval, "Approval"),
            HomeModel(R.drawable.iv_companydetails, "company Details"),
            HomeModel(R.drawable.iv_checkout, "Check out"),
            HomeModel(R.drawable.iv_distributorvisit, "Distributor Visit"),
            HomeModel(R.drawable.iv_fullfillment, "Fullfillment"),
            HomeModel(R.drawable.iv_genratecanopy, "Genrate Canopy Lead"),
            HomeModel(R.drawable.iv_leadenquiry, "Lead Enquiry"),
            HomeModel(R.drawable.iv_outletchain, "outlet visit"),
            HomeModel(R.drawable.iv_reports, "Report"),
            HomeModel(R.drawable.iv_ssdbregistration, "SS/DB REgistration"),
            HomeModel(R.drawable.iv_ssvisit, "SS Visit"),
            HomeModel(R.drawable.iv_tourplan, "Tour Plan"),
            HomeModel(R.drawable.iv_vansale, "Van Sale")
        )
    }

    private fun updateIndicators() {
        val visibleItemCount = rv_homesubmenu!!.layoutManager!!.childCount
        val totalItemCount = rv_homesubmenu!!.layoutManager!!.itemCount
        val firstVisibleItemPosition =
            (rv_homesubmenu!!.layoutManager as LinearLayoutManager?)!!.findFirstVisibleItemPosition()
        for (i in 0 until indicatorLayout!!.childCount) {
            indicatorLayout!!.getChildAt(i).setBackgroundResource(
                if (i == firstVisibleItemPosition) R.drawable.indicator_one else R.drawable.indicator_zero
            )
        }
    }

    private fun populateUI(employee: Employee) {
        val firstName    = employee.firstName
        val middleName = employee.middleName
        val lastName = employee.lastName
        val txtDesignation  = employee.designation

        tv_uname!!.text ="$firstName $middleName $lastName /$txtDesignation"
        Log.e("EmployeeViewModel", "employeeName: ${employee.firstName} \${employee.lastName")
        Log.e("EmployeeViewModel", "username: ${employee.username}")
    }

    override fun onItemClick(model: HomeModel) {
        Toast.makeText(requireContext(), "Item clicked:", Toast.LENGTH_SHORT).show()
        if (model.name == "Activity Form") {
            //startActivity(new Intent(getActivity(), "".class));
        } else if (model.name == "Approval") {
            //startActivity(new Intent(getActivity(), "".class));
        } else if (model.name == "company Details") {
            //startActivity(new Intent(getActivity(), "".class));
        } else if (model.name == "Check out") {
            //startActivity(new Intent(getActivity(), "".class));
        } else if (model.name == "Distributor Visit") {
            //startActivity(new Intent(getActivity(), "".class));
        } else if (model.name == "Fullfillment") {
            //startActivity(new Intent(getActivity(), "".class));
        } else if (model.name == "Genrate Canopy Lead") {
            //startActivity(new Intent(getActivity(), "".class));
        } else if (model.name == "Lead Enquiry") {
            //startActivity(new Intent(getActivity(), "".class));
        } else if (model.name == "outlet visit") {
            //startActivity(new Intent(getActivity(), "".class));
        } else if (model.name == "Report") {
            //startActivity(new Intent(getActivity(), "".class));
        } else if (model.name == "SS/DB REgistration") {
            //startActivity(new Intent(getActivity(), "".class));
        } else if (model.name == "SS Visit") {
            //startActivity(new Intent(getActivity(), "".class));
        } else if (model.name == "Tour Plan") {
            startActivity(Intent(activity, SelectTourPlanActivity::class.java))
        } else if (model.name == "Van Sale") {
            // startActivity(new Intent(getActivity(), "".class));
        }
    }


}