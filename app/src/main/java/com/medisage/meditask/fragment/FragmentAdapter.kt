package com.medisage.meditask.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class FragmentAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(
    fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
    var fragmentList: ArrayList<Fragment> = ArrayList()
    var fragmentTag: ArrayList<String> = ArrayList()

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTag[position]
    }

    fun addFragment(fragment: Fragment, tag: String) {
        fragmentList.add(fragment)
        fragmentTag.add(tag)
    }
}