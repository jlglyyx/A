package com.example.module_login.ui.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.lib_common.base.ui.activity.BaseActivity
import com.example.lib_common.constant.AppConstant
import com.example.module_login.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Route(path = AppConstant.RoutePath.SPLASH_ACTIVITY)
class SplashActivity : BaseActivity() {

    override fun getLayout(): Int {
        return R.layout.act_splash
    }

    override fun initData() {
    }

    override fun initView() {
        GlobalScope.launch {
            delay(1000)
            ARouter.getInstance().build(AppConstant.RoutePath.LOGIN_ACTIVITY).navigation()
            finish()
            //ARouter.getInstance().build(AppConstant.RoutePath.MAIN_ACTIVITY).navigation()
        }
    }

    override fun initViewModel() {
    }
}