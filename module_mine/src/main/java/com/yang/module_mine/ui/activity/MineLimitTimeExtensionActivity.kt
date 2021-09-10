package com.yang.module_mine.ui.activity

import android.graphics.Bitmap
import android.os.Environment
import com.alibaba.android.arouter.facade.annotation.Route
import com.huawei.hms.hmsscankit.ScanUtil
import com.yang.lib_common.base.ui.activity.BaseActivity
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.util.showShort
import com.yang.module_mine.R
import com.yang.module_mine.helper.getMineComponent
import com.yang.module_mine.viewmodel.MineViewModel
import kotlinx.android.synthetic.main.act_mine_limit_time_extension.*
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject


/**
 * @Author Administrator
 * @ClassName MineLimitTimeExtensionActivity
 * @Description
 * @Date 2021/9/10 11:53
 */
@Route(path = AppConstant.RoutePath.MINE_LIMIT_TIME_EXTENSION_ACTIVITY)
class MineLimitTimeExtensionActivity:BaseActivity() {
    @Inject
    lateinit var mineViewModel: MineViewModel

    override fun getLayout(): Int {
        return R.layout.act_mine_limit_time_extension
    }

    override fun initData() {
    }

    override fun initView() {
        createQRCode()
    }

    override fun initViewModel() {
        getMineComponent(this).inject(this)
    }

    private fun createQRCode(){
        val content = "QR Code Content"
        val width = 400
        val height = 400
        val buildBitmap = ScanUtil.buildBitmap(content, width, height)
        iv_qr_code.setImageBitmap(buildBitmap)

        iv_qr_code.setOnLongClickListener {
            val file = File("${Environment.getExternalStorageDirectory()}/MFiles/picture/${System.currentTimeMillis()}.jpg")
            try {
                val bufferedOutputStream = BufferedOutputStream(FileOutputStream(file))
                buildBitmap.compress(Bitmap.CompressFormat.JPEG,100,bufferedOutputStream)
                bufferedOutputStream.flush()
                bufferedOutputStream.close()
                showShort("二维码保存成功")
            }catch (e:Exception){
                e.printStackTrace()
                showShort("二维码保存失败")
            }


            return@setOnLongClickListener true
        }
    }
}