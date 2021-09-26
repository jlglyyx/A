package com.yang.module_mine.ui.obtain.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.yang.lib_common.base.ui.activity.BaseActivity
import com.yang.lib_common.constant.AppConstant
import com.yang.module_mine.R
import com.yang.module_mine.helper.getMineComponent
import com.yang.module_mine.viewmodel.MineViewModel
import javax.inject.Inject

/**
 * @Author Administrator
 * @ClassName AddAddressFragment
 * @Description 添加地址
 * @Date 2021/9/26 15:21
 */
@Route(path = AppConstant.RoutePath.MINE_ADD_ADDRESS_ACTIVITY)
class MineAddAddressActivity:BaseActivity() {

    @Inject
    lateinit var mineViewModel: MineViewModel

    override fun getLayout(): Int {
        return R.layout.act_mine_add_address
    }

    override fun initData() {
    }

    override fun initView() {
    }

    override fun initViewModel() {
        getMineComponent(this).inject(this)
    }
}