package com.yang.lib_common.interceptor

import android.content.Context
import android.util.Log
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.util.buildARouter
import com.yang.lib_common.util.getDefaultMMKV


/**
 * @ClassName LoginInterceptor
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2021/1/21 11:33
 */
@Interceptor(priority = 1, name = "登录状态拦截器")
class LoginInterceptor : IInterceptor {

    lateinit var mContext: Context

    companion object {
        private const val TAG = "LoginInterceptor"
    }

    override fun process(postcard: Postcard, callback: InterceptorCallback) {
        when (getDefaultMMKV().decodeInt(AppConstant.Constant.LOGIN_STATUS, -1)) {
            AppConstant.Constant.LOGIN_SUCCESS -> {
                callback.onContinue(postcard)
            }
            AppConstant.Constant.LOGIN_FAIL -> {
                buildARouter(AppConstant.RoutePath.LOGIN_ACTIVITY).navigation()
            }
            AppConstant.Constant.LOGIN_NO_PERMISSION -> {
                callback.onContinue(postcard)
            }
            else -> {
                buildARouter(AppConstant.RoutePath.LOGIN_ACTIVITY).navigation()
            }
        }
        Log.i(TAG, "process: 拦截器====${postcard.path}=====")
//        Log.i(TAG, "process: 拦截器====$postcard=====")
    }

    override fun init(context: Context) {

        mContext = context
        //仅会调用一次
    }

}