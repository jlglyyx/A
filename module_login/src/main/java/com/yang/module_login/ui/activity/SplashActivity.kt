package com.yang.module_login.ui.activity

import android.Manifest
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Environment
import android.os.IBinder
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.gson.Gson
import com.tbruyelle.rxpermissions3.RxPermissions
import com.yang.apt_annotation.annotain.InjectViewModel
import com.yang.lib_common.base.ui.activity.BaseActivity
import com.yang.lib_common.bus.event.UIChangeLiveData
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.down.DownLoadManagerService
import com.yang.lib_common.down.thread.MultiMoreThreadDownload
import com.yang.lib_common.proxy.InjectViewModelProxy
import com.yang.lib_common.room.entity.UserInfoData
import com.yang.lib_common.util.*
import com.yang.module_login.R
import com.yang.module_login.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.act_splash.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@Route(path = AppConstant.RoutePath.SPLASH_ACTIVITY)
class SplashActivity : BaseActivity() {

    @Inject
    lateinit var gson: Gson

    @InjectViewModel
    lateinit var loginViewModel: LoginViewModel

    lateinit var downLoadManagerBinder: DownLoadManagerService.DownLoadManagerBinder

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            downLoadManagerBinder = service as DownLoadManagerService.DownLoadManagerBinder
//            downLoadManagerBinder.startDown(
//                DownLoadManager.Builder()
//                    .threadSize(10)
//                    .filePath("${Environment.getExternalStorageDirectory()}/MFiles/video/aaa.mp4")
////                    .filePath("${Environment.getExternalStorageDirectory()}/MFiles/video/${System.currentTimeMillis()}_a.mp4")
//                    //.url("https://scpic.chinaz.net/files/pic/pic9/202107/bpic23810.jpg")
//                    .url("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4")
////                    .url("https://93d2aeafc06b4b601cbc9bc1fd283894.dlied1.cdntips.net/dlied1.qq.com/qqweb/QQ_1/android_apk/Android_8.8.50.6735_537101929.32.HB2.apk?mkey=61a9c6201b117579&f=0000&cip=27.17.83.140&proto=https&access_type=")
//                    .createNewFile(true)
//            )
        }

        override fun onServiceDisconnected(name: ComponentName?) {

        }

    }

    override fun getLayout(): Int {
        return R.layout.act_splash
    }

    override fun initData() {
        //downPicture()
        val intent = Intent(this, DownLoadManagerService::class.java)
        bindService(intent, serviceConnection, BIND_AUTO_CREATE)
        startService(intent)
    }

    override fun initView() {

        initPermission()

    }

    private fun downVideo() {
        val file = File("${Environment.getExternalStorageDirectory()}/MFiles/video/register.mp4")
        if (file.exists()) {
            return
        }
        MultiMoreThreadDownload.Builder(this)
            .parentFilePath("${Environment.getExternalStorageDirectory()}/MFiles/video")
            .filePath("register.mp4")
            .threadNum(3)
            .fileUrl("https://media.w3.org/2010/05/sintel/trailer.mp4")
            .showNotice(false)
            .build()
            .start()
    }

    private fun initPermission() {
        RxPermissions(this).requestEachCombined(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE
        )
            .subscribe {
                when {
                    it.granted -> {
                        val userInfo = getUserInfo()
                        //downVideo()
                        val launch = lifecycleScope.launch {
                            for (i in 1 downTo 0) {
                                tv_timer.text = "${i}s点击跳过"
                                delay(1000)
                            }
                            toLogin(userInfo)
                        }

                        tv_timer.clicks().subscribe {
                            launch.cancel()
                            toLogin(userInfo)
                        }
                        toLogin(userInfo)
                    }
                    it.shouldShowRequestPermissionRationale -> {

                        showShort("逼崽子把权限关了还怎么玩，赶紧打开")
                    }
                    else -> {
                        showShort("逼崽子把权限关了还怎么玩，赶紧打开")
                    }
                }
            }
    }


    private fun toLogin(userInfo: UserInfoData?) {
        if (null == userInfo) {
            buildARouter(AppConstant.RoutePath.LOGIN_ACTIVITY)
                .withOptionsCompat(
                    ActivityOptionsCompat.makeCustomAnimation(
                        this@SplashActivity,
                        0,
                        0
                    )
                )
                .navigation(this@SplashActivity)
            finish()
        } else {
            loginViewModel.splashLogin(userInfo.userAccount, userInfo.userPassword)
            loginViewModel.mUserInfoData.observe(this, Observer { mUserInfo ->
                getDefaultMMKV().encode(
                    AppConstant.Constant.LOGIN_STATUS,
                    AppConstant.Constant.LOGIN_SUCCESS
                )
                updateUserInfo(mUserInfo)
                buildARouter(AppConstant.RoutePath.MAIN_ACTIVITY).withOptionsCompat(
                    ActivityOptionsCompat.makeCustomAnimation(
                        this@SplashActivity,
                        R.anim.fade_in,
                        R.anim.fade_out
                    )
                ).navigation()
                finish()
            })
        }
    }


    override fun initUIChangeLiveData(): UIChangeLiveData {
        return loginViewModel.uC
    }

    override fun initViewModel() {
        InjectViewModelProxy.inject(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleScope.cancel()
    }
}