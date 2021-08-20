package com.yang.lib_common.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


/**
 * @ClassName TabAndViewPagerAdapter
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/12/1 14:52
 */
class TabAndViewPagerAdapter(fragmentActivity: FragmentActivity,private val fragments:MutableList<Fragment>,private val titles:MutableList<String>) : FragmentStateAdapter(fragmentActivity) {


    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}
class TabAndViewPagerFragmentAdapter(fragment: Fragment,private val fragments:MutableList<Fragment>,private val titles:MutableList<String>) : FragmentStateAdapter(fragment) {


    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}