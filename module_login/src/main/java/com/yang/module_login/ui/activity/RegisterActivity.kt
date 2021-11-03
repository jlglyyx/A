package com.yang.module_login.ui.activity

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.yang.lib_common.base.ui.activity.BaseActivity
import com.yang.lib_common.bus.event.UIChangeLiveData
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.data.UserInfoData
import com.yang.lib_common.util.clicks
import com.yang.lib_common.util.showShort
import com.yang.module_login.R
import com.yang.module_login.helper.getLoginComponent
import com.yang.module_login.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.act_register.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@Route(path = AppConstant.RoutePath.REGISTER_ACTIVITY)
class RegisterActivity : BaseActivity() {

    @Inject
    lateinit var loginViewModel: LoginViewModel

    override fun getLayout(): Int {
        return R.layout.act_register
    }

    override fun initData() {
    }

    override fun initView() {
        lifecycle.addObserver(isv_image)
        Glide.with(this).asBitmap().load("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1.zhimg.com%2F50%2Fv2-aa0e39f579e586cb84e57737d7fe4a32_hd.jpg&refer=http%3A%2F%2Fpic1.zhimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1638520579&t=6ff895868c8fabaed70f7171e4aa8061").into(
            object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Log.i(TAG, "onResourceReady: $resource")
                    isv_image.setBitMap(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }

            })
        bt_register.clicks().subscribe {
            checkForm()
        }
        tv_verification_code.clicks().subscribe {
            initTimer()
        }
        tv_to_login.clicks().subscribe {
            finish()
        }
    }
    override fun initUIChangeLiveData(): UIChangeLiveData? {
        return loginViewModel.uC
    }

    override fun initViewModel() {
        getLoginComponent(this).inject(this)
    }

    private fun checkForm(){
        if (TextUtils.isEmpty(et_user.text.toString())){
            showShort("请输入账号")
            return
        }
        if (TextUtils.isEmpty(et_password.text.toString())){
            showShort("请输入密码")
            return
        }
        if (TextUtils.isEmpty(et_confirm_password.text.toString())){
            showShort("请确认密码")
            return
        }
        if (!TextUtils.equals(et_password.text.toString(),et_confirm_password.text.toString())){
            showShort("两次密码不一致")
            return
        }
        val userInfoData = UserInfoData(
            null,
            null,
            et_user.text.toString(),
            et_user.text.toString(),
            et_password.text.toString(),
            null,
            null,
            0,
            null,
            null,
            null
        )
        loginViewModel.register(userInfoData)
        loginViewModel.mUserInfoData.observe(this, Observer {
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