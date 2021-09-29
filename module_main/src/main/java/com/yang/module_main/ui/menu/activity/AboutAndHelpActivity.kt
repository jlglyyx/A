package com.yang.module_main.ui.menu.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.yang.lib_common.base.ui.activity.BaseActivity
import com.yang.lib_common.constant.AppConstant
import com.yang.module_main.R

/**
 * @Author Administrator
 * @ClassName AboutAndHelpActivity
 * @Description 关于与帮助
 * @Date 2021/9/28 10:47
 */
@Route(path = AppConstant.RoutePath.ABOUT_AND_HELP_ACTIVITY)
class AboutAndHelpActivity:BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.act_about_and_help
    }

    override fun initData() {
    }

    override fun initView() {
    }

    override fun initViewModel() {
    }
}