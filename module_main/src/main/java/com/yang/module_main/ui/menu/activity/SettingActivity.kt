package com.yang.module_main.ui.menu.activity

import android.os.Environment
import com.alibaba.android.arouter.facade.annotation.Route
import com.yang.lib_common.base.ui.activity.BaseActivity
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.util.formatSize
import com.yang.lib_common.util.getAllFileSize
import com.yang.module_main.R
import kotlinx.android.synthetic.main.act_setting.*
import java.io.File

/**
 * @Author Administrator
 * @ClassName SettingActivity
 * @Description 设置
 * @Date 2021/9/28 10:47
 */
@Route(path = AppConstant.RoutePath.SETTING_ACTIVITY)
class SettingActivity:BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.act_setting
    }

    override fun initData() {
    }

    override fun initView() {
        icv_cache.rightContent = formatSize(getAllFileSize(File("${Environment.getExternalStorageDirectory()}/MFiles")))
    }

    override fun initViewModel() {
    }
}