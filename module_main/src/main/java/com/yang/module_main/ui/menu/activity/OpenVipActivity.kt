package com.yang.module_main.ui.menu.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.yang.lib_common.base.ui.activity.BaseActivity
import com.yang.lib_common.constant.AppConstant
import com.yang.module_main.R

/**
 * @Author Administrator
 * @ClassName OpenVipActivity
 * @Description 开通会员
 * @Date 2021/9/28 10:47
 */
@Route(path = AppConstant.RoutePath.OPEN_VIP_ACTIVITY)
class OpenVipActivity:BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.act_open_vip
    }

    override fun initData() {
    }

    override fun initView() {
    }

    override fun initViewModel() {
    }
}