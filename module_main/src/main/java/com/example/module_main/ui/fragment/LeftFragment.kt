package com.example.module_main.ui.fragment

import com.bumptech.glide.Glide
import com.example.lib_common.base.ui.fragment.BaseFragment
import com.example.lib_common.constant.AppConstant
import com.example.lib_common.help.buildARouter
import com.example.lib_common.util.clicks
import com.example.module_main.R
import kotlinx.android.synthetic.main.fra_left.*

class LeftFragment : BaseFragment() {
    override fun getLayout(): Int {
        return R.layout.fra_left
    }

    override fun initData() {
    }

    override fun initView() {
        Glide.with(this).load("https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg").into(siv_head)
        siv_head.clicks().subscribe {
            buildARouter(AppConstant.RoutePath.OTHER_PERSON_INFO_ACTIVITY).navigation()
        }
    }

    override fun initViewModel() {
    }

//    override fun setStatusPadding(): Boolean {
//        return true
//    }
}