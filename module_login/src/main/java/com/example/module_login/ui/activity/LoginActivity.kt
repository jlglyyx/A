package com.example.module_login.ui.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.lib_common.base.ui.activity.BaseActivity
import com.example.lib_common.constant.AppConstant
import com.example.module_login.R

@Route(path = AppConstant.RoutePath.LOGIN_ACTIVITY)
class LoginActivity : BaseActivity() {


    override fun getLayout(): Int {
        return R.layout.act_login
    }

    override fun initData() {
    }

    override fun initView() {
    }

    override fun initViewModel() {
    }
}