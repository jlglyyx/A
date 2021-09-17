package com.yang.module_login.ui.activity

import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.gson.Gson
import com.yang.lib_common.base.ui.activity.BaseActivity
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.util.buildARouter
import com.yang.lib_common.util.clicks
import com.yang.lib_common.util.getDefaultMMKV
import com.yang.lib_common.util.getUserInfo
import com.yang.module_login.R
import com.yang.module_login.helper.getLoginComponent
import com.yang.module_login.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.act_splash.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@Route(path = AppConstant.RoutePath.SPLASH_ACTIVITY)
class SplashActivity : BaseActivity() {

    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var loginViewModel: LoginViewModel

    override fun getLayout(): Int {
        return R.layout.act_splash
    }

    override fun initData() {
    }

    override fun initView() {

        val userInfo = getUserInfo()
        if (null == userInfo) {
            val launch = lifecycleScope.launch {
//            for (i in 3 downTo 0) {
//                tv_timer.text = "${i}s点击跳过"
//                delay(1000)
//            }

                android.R.anim.slide_out_right

  //             buildARouter(AppConstant.RoutePath.LOGIN_ACTIVITY).navigation(this@SplashActivity)
                buildARouter(AppConstant.RoutePath.LOGIN_ACTIVITY).withTransition(
                    0,
                    0
                ).navigation(this@SplashActivity)
                finish()
            }

            tv_timer.clicks().subscribe {
                launch.cancel()
                buildARouter(AppConstant.RoutePath.LOGIN_ACTIVITY).navigation(this@SplashActivity)
                finish()
            }

        } else {

            loginViewModel.login(userInfo.userAccount, userInfo.userPassword)
            loginViewModel.mUserInfoData.observe(this, Observer {
                getDefaultMMKV().encode(AppConstant.Constant.USER_INFO, gson.toJson(it))
                buildARouter(AppConstant.RoutePath.MAIN_ACTIVITY).navigation()
                finish()
            })

            tv_timer.clicks().subscribe {
                buildARouter(AppConstant.RoutePath.MAIN_ACTIVITY).withTransition(R.anim.fade_in, 0)
                    .navigation(this@SplashActivity)
                finish()
            }
        }
    }

    override fun initViewModel() {
        getLoginComponent(this).inject(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleScope.cancel()
    }
}