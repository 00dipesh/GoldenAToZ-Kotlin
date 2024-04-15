package com.goldendigitech.goldenatoz.Leave.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.goldendigitech.goldenatoz.Leave.Adapter.LeaveAdapter
import com.goldendigitech.goldenatoz.Leave.Response.LeaveViewModel
import com.goldendigitech.goldenatoz.MainActivity
import com.goldendigitech.goldenatoz.R
import com.goldendigitech.goldenatoz.singleToneClass.SharedPreferencesManager


class RejectFragment : Fragment() {

    lateinit var leaveAdapter: LeaveAdapter
    lateinit var leaveViewModel: LeaveViewModel
    lateinit var rv_reject: RecyclerView
    lateinit var yes_btn: Button
    lateinit var rl_alert: RelativeLayout
    val leaveStatus: String = "reject"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = LayoutInflater.from(container?.context).inflate(R.layout.fragment_reject, container, false)

        val employeeId = SharedPreferencesManager.getInstance(requireContext()).getUserId()

        leaveViewModel = ViewModelProvider(this).get(LeaveViewModel::class.java)

        rv_reject = view.findViewById(R.id.rv_reject)
        yes_btn = view.findViewById(R.id.yes_btn)
        rl_alert = view.findViewById(R.id.rl_alert)


        leaveViewModel.leaveStatusLiveData.observe(viewLifecycleOwner, Observer { leaveStatus ->
            if (leaveStatus != null  && leaveStatus.isNotEmpty()) {
                val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
                rv_reject.layoutManager = mLayoutManager
                leaveAdapter = LeaveAdapter(requireContext(), mutableListOf())
                leaveAdapter.setLeaveModelList(leaveStatus)
                rv_reject.adapter = leaveAdapter
                rl_alert.visibility = View.GONE
            } else {
                // Handle error state
                rl_alert.visibility = View.VISIBLE
            }

        })

        leaveViewModel.hanldeLeaveStatus(employeeId,leaveStatus)

        yes_btn.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }
        return view

    }


}