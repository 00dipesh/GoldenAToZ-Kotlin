package com.goldendigitech.goldenatoz.analysis

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.goldendigitech.goldenatoz.R
import com.google.android.material.tabs.TabLayout

class AnalysisFragment : Fragment() {

    lateinit var viewpager_analysis :ViewPager
    lateinit var tab_analysis :TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_analysis, container, false)

        viewpager_analysis = view.findViewById(R.id.viewpager_analysis)
        tab_analysis = view.findViewById(R.id.tab_analysis)

        val fragmentList = listOf(
            UserFragment(),
            DistributorFragment(),
            BeatFragment(),
            RetailerFragment()
        )

        val tabTitles = arrayOf("USER", "DISTRIBUTOR", "BEAT", "RETAILER")


        val analysisAdapter = AnalysisAdapter(childFragmentManager,fragmentList,tabTitles)
        viewpager_analysis.adapter = analysisAdapter
        tab_analysis.setupWithViewPager(viewpager_analysis)


        tab_analysis.setSelectedTabIndicatorColor(resources.getColor(android.R.color.holo_red_dark))
        tab_analysis.setTabTextColors(resources.getColor(R.color.white), resources.getColor(android.R.color.white))

        tab_analysis.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
               tab?.view?.setBackgroundColor(ContextCompat.getColor(requireContext(),android.R.color.transparent))
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }


        })
        return view
    }


}