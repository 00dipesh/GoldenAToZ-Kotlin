package com.goldendigitech.goldenatoz.Adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.goldendigitech.goldenatoz.Performance.PerformanceFragment
import com.goldendigitech.goldenatoz.ProductView.ProductviewFragment
import com.goldendigitech.goldenatoz.analysis.AnalysisFragment

class TabLayoutAdapter( private val mContext:Context,fragmentManager: FragmentManager,private val mTitaltabs :Int) :FragmentPagerAdapter(fragmentManager,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int =  mTitaltabs

    override fun getItem(position: Int): Fragment = when (position) {

        0 -> PerformanceFragment()

        1 -> ProductviewFragment()

        2 -> AnalysisFragment()
        else -> throw IllegalArgumentException("Invalid position: $position")
    }

}