package com.yang.module_main.ui.menu.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.yang.lib_common.base.ui.activity.BaseActivity
import com.yang.lib_common.constant.AppConstant
import com.yang.module_main.R

/**
 * @Author Administrator
 * @ClassName SettingActivity
 * @Description 设置
 * @Date 2021/9/28 10:47
 */
@Route(path = AppConstant.RoutePath.SETTING_ACTIVITY)
class SettingActivity:BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.act_setting
    }

    override fun initData() {
    }

    override fun initView() {
    }

    override fun initViewModel() {
    }
}