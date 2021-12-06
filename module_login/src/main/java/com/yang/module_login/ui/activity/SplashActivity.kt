package com.yang.module_login.ui.activity

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
import com.yang.lib_common.base.ui.activity.BaseActivity
import com.yang.lib_common.bus.event.UIChangeLiveData
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.down.DownLoadManagerService
import com.yang.lib_common.down.thread.MultiMoreThreadDownload
import com.yang.lib_common.util.buildARouter
import com.yang.lib_common.util.clicks
import com.yang.lib_common.util.getDefaultMMKV
import com.yang.lib_common.util.getUserInfo
import com.yang.module_login.R
import com.yang.module_login.helper.getLoginComponent
import com.yang.module_login.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.act_splash.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@Route(path = AppConstant.RoutePath.SPLASH_ACTIVITY)
class SplashActivity : BaseActivity() {

    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var loginViewModel: LoginViewModel

    lateinit var downLoadManagerBinder: DownLoadManagerService.DownLoadManagerBinder

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            downLoadManagerBinder = service as DownLoadManagerService.DownLoadManagerBinder
//            downLoadManagerBinder.startDown(
//                DownLoadManager.Builder()
//                    .threadSize(10)
//                    .filePath("${Environment.getExternalStorageDirectory()}/MFiles/picture/${System.currentTimeMillis()}_a.jpg")
//                    .url("https://scpic.chinaz.net/files/pic/pic9/202107/bpic23810.jpg")
//                    //.url("https://93d2aeafc06b4b601cbc9bc1fd283894.dlied1.cdntips.net/dlied1.qq.com/qqweb/QQ_1/android_apk/Android_8.8.50.6735_537101929.32.HB2.apk?mkey=61a9c6201b117579&f=0000&cip=27.17.83.140&proto=https&access_type=")
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
        val userInfo = getUserInfo()
        if (null == userInfo) {
            val launch = lifecycleScope.launch {
                for (i in 1 downTo 0) {
                    tv_timer.text = "${i}s点击跳过"
                    delay(1000)
                }

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
            }

            tv_timer.clicks().subscribe {
                launch.cancel()
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
            }

        } else {

            loginViewModel.splashLogin(userInfo.userAccount, userInfo.userPassword)
            loginViewModel.mUserInfoData.observe(this, Observer {
                getDefaultMMKV().encode(AppConstant.Constant.LOGIN_STATUS,AppConstant.Constant.LOGIN_SUCCESS)
                getDefaultMMKV().encode(AppConstant.Constant.USER_INFO, gson.toJson(it))
                buildARouter(AppConstant.RoutePath.MAIN_ACTIVITY).withOptionsCompat(
                    ActivityOptionsCompat.makeCustomAnimation(
                        this@SplashActivity,
                        R.anim.fade_in,
                        R.anim.fade_out
                    )
                ).navigation()
                finish()
            })

            tv_timer.clicks().subscribe {
                getDefaultMMKV().encode(AppConstant.Constant.LOGIN_STATUS,AppConstant.Constant.LOGIN_NO_PERMISSION)
                buildARouter(AppConstant.RoutePath.MAIN_ACTIVITY).withOptionsCompat(
                    ActivityOptionsCompat.makeCustomAnimation(
                        this@SplashActivity,
                        R.anim.fade_in,
                        R.anim.fade_out
                    )
                )
                    .navigation(this@SplashActivity)
                finish()
            }
        }

    }

    private fun downPicture(){
        MultiMoreThreadDownload.Builder(this)
            .parentFilePath("${Environment.getExternalStorageDirectory()}/MFiles/picture")
            .filePath("login.png")
            .threadNum(3)
            .fileUrl("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic2.zhimg.com%2Fv2-583a86cd154739160d2e17e185dcc8f2_r.jpg%3Fsource%3D1940ef5c&refer=http%3A%2F%2Fpic2.zhimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1638427892&t=e2a584b32bb0b6f820613078d552716c")
            .showNotice(false)
            .build()
            .start()
        MultiMoreThreadDownload.Builder(this)
            .parentFilePath("${Environment.getExternalStorageDirectory()}/MFiles/picture")
            .filePath("register.png")
            .threadNum(3)
            .fileUrl("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1.zhimg.com%2F50%2Fv2-aa0e39f579e586cb84e57737d7fe4a32_hd.jpg&refer=http%3A%2F%2Fpic1.zhimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1638520579&t=6ff895868c8fabaed70f7171e4aa8061")
            .showNotice(false)
            .build()
            .start()
    }


    override fun initUIChangeLiveData(): UIChangeLiveData {
        return loginViewModel.uC
    }

    override fun initViewModel() {
        getLoginComponent(this).inject(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleScope.cancel()
    }
}