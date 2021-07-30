package com.example.module_login.ui.activity

import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.lib_common.base.ui.activity.BaseActivity
import com.example.lib_common.constant.AppConstant
import com.example.lib_common.help.buildARouter
import com.example.lib_common.util.clicks
import com.example.module_login.R
import kotlinx.android.synthetic.main.act_splash.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@Route(path = AppConstant.RoutePath.SPLASH_ACTIVITY)
class SplashActivity : BaseActivity() {

    override fun getLayout(): Int {
        return R.layout.act_splash
    }

    override fun initData() {
    }

    override fun initView() {
        val launch = lifecycleScope.launch(Dispatchers.Main) {
            buildARouter(AppConstant.RoutePath.MAIN_ACTIVITY).navigation(this@SplashActivity)
//            buildARouter(AppConstant.RoutePath.MAIN_ACTIVITY).withTransition(R.anim.fade_in, R.anim.fade_out).navigation(this@SplashActivity)
            finish()
        }
        tv_timer.clicks().subscribe {
            launch.cancel()
            buildARouter(AppConstant.RoutePath.MAIN_ACTIVITY).withTransition(R.anim.fade_in, 0)
                .navigation(this@SplashActivity)
            finish()
        }
//        val launch = lifecycleScope.launch {
//            for (i in 3 downTo 0) {
//                tv_timer.text = "${i}s点击跳过"
//                delay(1000)
//            }
//            buildARouter(AppConstant.RoutePath.LOGIN_ACTIVITY).navigation()
//            finish()
//        }
//
//        tv_timer.clicks().subscribe{
//            launch.cancel()
//            buildARouter(AppConstant.RoutePath.LOGIN_ACTIVITY).navigation()
//            finish()
//        }

    }

    override fun initViewModel() {
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleScope.cancel()
    }
}