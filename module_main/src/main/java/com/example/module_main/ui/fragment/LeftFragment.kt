package com.example.module_main.ui.fragment

import com.example.lib_common.base.ui.fragment.BaseFragment
import com.example.module_main.R

class LeftFragment : BaseFragment() {
    override fun getLayout(): Int {
        return R.layout.fra_left
    }

    override fun initData() {
    }

    override fun initView() {

        //statusBar?.setBackgroundResource(android.R.color.holo_green_light)
    }

    override fun initViewModel() {
    }

//    override fun setStatusPadding(): Boolean {
//        return true
//    }
}