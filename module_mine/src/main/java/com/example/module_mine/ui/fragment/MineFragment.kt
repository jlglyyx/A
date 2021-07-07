package com.example.module_mine.ui.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.lib_common.base.ui.fragment.BaseLazyFragment
import com.example.lib_common.constant.AppConstant
import com.example.module_mine.R

@Route(path = AppConstant.RoutePath.MINE_FRAGMENT)
class MineFragment : BaseLazyFragment() {
    override fun getLayout(): Int {
        return R.layout.fra_mine
    }

    override fun initView() {
    }

    override fun initData() {

    }

    override fun initViewModel() {

    }


}