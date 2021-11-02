package com.yang.module_login.ui.activity

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.Log
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
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
        //initVideoView()

        lifecycle.addObserver(isv_image)
        Glide.with(this).asBitmap().load("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic2.zhimg.com%2Fv2-583a86cd154739160d2e17e185dcc8f2_r.jpg%3Fsource%3D1940ef5c&refer=http%3A%2F%2Fpic2.zhimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1638427892&t=e2a584b32bb0b6f820613078d552716c").into(
            object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Log.i(TAG, "onResourceReady: $resource")
                    isv_image.setBitMap(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }

            })


        bt_login.clicks().subscribe {
            checkForm()
            //buildARouter(AppConstant.RoutePath.MAIN_ACTIVITY).navigation()
        }
        tv_not_login.clicks().subscribe {
            buildARouter(AppConstant.RoutePath.MAIN_ACTIVITY).withOptionsCompat(
                ActivityOptionsCompat.makeCustomAnimation(
                    this@LoginActivity,
                    R.anim.fade_in,
                    R.anim.fade_out
                )
            ).navigation()
            finish()
        }

        tv_verification_code.clicks().subscribe {
            initTimer()
        }
        tv_to_register.clicks().subscribe {
            buildARouter(AppConstant.RoutePath.REGISTER_ACTIVITY).navigation()
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
            getDefaultMMKV().encode(AppConstant.Constant.USER_INFO, gson.toJson(it))
            buildARouter(AppConstant.RoutePath.MAIN_ACTIVITY).withOptionsCompat(
                ActivityOptionsCompat.makeCustomAnimation(
                    this@LoginActivity,
                    R.anim.fade_in,
                    R.anim.fade_out
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


    override fun onDestroy() {
        super.onDestroy()
        lifecycleScope.cancel()
    }
}