package com.example.module_login.ui.activity

import android.content.Intent
import com.example.lib_common.base.ui.activity.BaseActivity
import com.example.module_login.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity() {

    override fun getLayout(): Int {
        return R.layout.act_splash
    }

    override fun initData() {
    }

    override fun initView() {
        GlobalScope.launch {
            delay(1000)
            startActivity(Intent(this@SplashActivity,LoginActivity::class.java))
            //ARouter.getInstance().build(AppConstant.RoutePath.MAIN_ACTIVITY).navigation()
        }
    }

    override fun initViewModel() {
    }
}