package com.yang.module_login.ui.activity

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import android.os.Environment
import android.text.TextUtils
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.gson.Gson
import com.yang.apt_annotation.annotain.InjectViewModel
import com.yang.lib_common.base.ui.activity.BaseActivity
import com.yang.lib_common.bus.event.UIChangeLiveData
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.data.MediaInfoBean
import com.yang.lib_common.proxy.InjectViewModelProxy
import com.yang.lib_common.util.buildARouter
import com.yang.lib_common.util.clicks
import com.yang.lib_common.util.getDefaultMMKV
import com.yang.lib_common.util.showShort
import com.yang.module_login.R
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

    @InjectViewModel
    lateinit var loginViewModel: LoginViewModel

    private var videoUrl = "${Environment.getExternalStorageDirectory()}/MFiles/video/aaa.mp4"

    private var mediaPlayer:MediaPlayer? = null

    override fun getLayout(): Int {
        return R.layout.act_login
    }

    override fun initData() {

        Log.i(TAG, "initData: $loginViewModel")
    }

    override fun initView() {
        initVideoView()

        val registerForActivityResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK && null != it.data) {
                    it.data!!.getParcelableArrayListExtra<MediaInfoBean>(AppConstant.Constant.DATA)
                        ?.let { beans ->
                            videoUrl = beans[0].filePath.toString()
                            surfaceView.restartVideo(videoUrl)
                        }
                }
            }



        bt_login.clicks().subscribe {

            val forName = Class.forName("com.yang.module_main.ui.main.activity.PictureSelectActivity")
            val intent = Intent(this,forName)
            intent.putExtra(AppConstant.Constant.TYPE, AppConstant.Constant.NUM_TWO)
            intent.putExtra(AppConstant.Constant.NUM, AppConstant.Constant.NUM_ONE)
            registerForActivityResult.launch(intent)

            //checkForm()
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


    /**
     * 1.去除loginViewModelFactory
     * 2.去除getLoginComponent(this).inject(this)
     * 3.去除loginViewModel = getViewModel(loginViewModelFactory,LoginViewModel::class.java)
     */

    override fun initViewModel() {
        InjectViewModelProxy.inject(this)
    }

    override fun initUIChangeLiveData(): UIChangeLiveData? {
        return loginViewModel.uC
    }

    private fun checkForm() {
        if (TextUtils.isEmpty(et_user.text.toString())) {
            showShort(getString(R.string.string_input_account))
            return
        }
        if (TextUtils.isEmpty(et_password.text.toString())) {
            showShort(getString(R.string.string_input_password))
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
            tv_verification_code.text = getString(R.string.string_re_get_verification_code)
        }
    }

    private fun initVideoView() {
        lifecycle.addObserver(surfaceView)
        mediaPlayer = surfaceView.initMediaPlayer(videoUrl)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleScope.cancel()
    }
}