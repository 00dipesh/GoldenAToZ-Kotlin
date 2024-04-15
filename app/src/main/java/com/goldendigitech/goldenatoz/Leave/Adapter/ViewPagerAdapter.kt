package com.goldendigitech.goldenatoz.Leave.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.goldendigitech.goldenatoz.Leave.Fragment.ApproveFragment
import com.goldendigitech.goldenatoz.Leave.Fragment.PendingFragment
import com.goldendigitech.goldenatoz.Leave.Fragment.RejectFragment

class ViewPagerAdapter (fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> PendingFragment()
            1 -> ApproveFragment()
            2 -> RejectFragment()
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Pending"
            1 -> "Approve"
            2 -> "Reject"
            else -> null
        }
    }
}