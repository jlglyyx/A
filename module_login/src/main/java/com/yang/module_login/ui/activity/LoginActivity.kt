package com.yang.module_login.ui.activity

import android.media.MediaPlayer
import android.os.Environment
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.gson.Gson
import com.yang.lib_common.base.ui.activity.BaseActivity
import com.yang.lib_common.bus.event.UIChangeLiveData
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.util.buildARouter
import com.yang.lib_common.util.clicks
import com.yang.lib_common.util.getDefaultMMKV
import com.yang.lib_common.util.showShort
import com.yang.module_login.R
import com.yang.module_login.helper.getLoginComponent
import com.yang.module_login.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.act_login.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@Route(path = AppConstant.RoutePath.LOGIN_ACTIVITY)
class LoginActivity : BaseActivity() {

    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var loginViewModel: LoginViewModel


    override fun getLayout(): Int {
        return R.layout.act_login
    }

    override fun initData() {

    }

    override fun initView() {
        initVideoView()
        bt_login.clicks().subscribe {
            checkForm()
            //buildARouter(AppConstant.RoutePath.MAIN_ACTIVITY).navigation()
        }
        tv_not_login.clicks().subscribe {
            getDefaultMMKV().encode(
                AppConstant.Constant.LOGIN_STATUS,
                AppConstant.Constant.LOGIN_NO_PERMISSION
            )
            buildARouter(AppConstant.RoutePath.MAIN_ACTIVITY).withOptionsCompat(
                ActivityOptionsCompat.makeCustomAnimation(
                    this@LoginActivity,
                    R.anim.bottom_in,
                    R.anim.bottom_out
                )
            ).navigation()
            finish()
        }

        tv_verification_code.clicks().subscribe {
            initTimer()
        }
        tv_to_register.clicks().subscribe {
            buildARouter(AppConstant.RoutePath.REGISTER_ACTIVITY).withOptionsCompat(
                ActivityOptionsCompat.makeCustomAnimation(
                    this@LoginActivity, R.anim.fade_in,
                    R.anim.fade_out
                )
            ).navigation()


        }
        iv_icon.clicks().subscribe {
            buildARouter(AppConstant.RoutePath.CONNECT_ADDRESS_ACTIVITY).navigation()
        }
    }

    override fun initViewModel() {
        getLoginComponent(this).inject(this)
    }

    override fun initUIChangeLiveData(): UIChangeLiveData? {
        return loginViewModel.uC
    }

    private fun checkForm() {
        if (TextUtils.isEmpty(et_user.text.toString())) {
            showShort("请输入账号")
            return
        }
        if (TextUtils.isEmpty(et_password.text.toString())) {
            showShort("请输入密码")
            return
        }
        loginViewModel.login(et_user.text.toString(), et_password.text.toString())
        loginViewModel.mUserInfoData.observe(this, Observer {
            getDefaultMMKV().encode(
                AppConstant.Constant.LOGIN_STATUS,
                AppConstant.Constant.LOGIN_SUCCESS
            )
            getDefaultMMKV().encode(AppConstant.Constant.USER_INFO, gson.toJson(it))
            buildARouter(AppConstant.RoutePath.MAIN_ACTIVITY).withOptionsCompat(
                ActivityOptionsCompat.makeCustomAnimation(
                    this@LoginActivity,
                    R.anim.bottom_in,
                    R.anim.bottom_out
                )
            ).navigation()
            finish()
        })
    }

    private fun initTimer() {
        lifecycleScope.launch {
            tv_verification_code.isClickable = false
            for (i in 60 downTo 0) {
                tv_verification_code.text = "${i}秒后获取验证码"
                delay(1000)
            }
            tv_verification_code.isClickable = true
            tv_verification_code.text = "重新获取验证码"
        }
    }

    private fun initVideoView() {
        lifecycle.addObserver(surfaceView)
        val mediaPlayer = surfaceView.initMediaPlayer("${Environment.getExternalStorageDirectory()}/MFiles/video/aaa.mp4")
        mediaPlayer?.setOnInfoListener { mp, what, extra ->
            if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                iv_cover.visibility = View.GONE
            }
            Log.i(TAG, "setOnInfoListener: $mp   $what   $extra")
            return@setOnInfoListener false
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        lifecycleScope.cancel()
    }
}