package com.example.module_mine.ui.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.example.lib_common.base.ui.fragment.BaseLazyFragment
import com.example.lib_common.constant.AppConstant
import com.example.module_mine.R
import kotlinx.android.synthetic.main.fra_mine.*

@Route(path = AppConstant.RoutePath.MINE_FRAGMENT)
class MineFragment : BaseLazyFragment() {
    override fun getLayout(): Int {
        return R.layout.fra_mine
    }

    override fun initView() {
        Glide.with(this).load("https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg").into(siv_img)
    }

    override fun initData() {

    }

    override fun initViewModel() {

    }


}