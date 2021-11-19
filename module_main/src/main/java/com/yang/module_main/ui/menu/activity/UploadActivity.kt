package com.yang.module_main.ui.menu.activity

import android.content.Intent
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.lxj.xpopup.XPopup
import com.yang.lib_common.base.ui.activity.BaseActivity
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.data.MediaInfoBean
import com.yang.lib_common.util.buildARouter
import com.yang.lib_common.util.clicks
import com.yang.lib_common.util.showShort
import com.yang.module_main.R
import com.yang.module_main.helper.getMainComponent
import com.yang.module_main.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.act_upload.*
import java.util.*
import javax.inject.Inject

/**
 * @Author Administrator
 * @ClassName UploadActivity
 * @Description
 * @Date 2021/11/19 11:08
 */
@Route(path = AppConstant.RoutePath.UPLOAD_ACTIVITY)
class UploadActivity : BaseActivity(){

    private var fileTypeArray = arrayOf("图片","视频")

    private var selectFileType = ""

    private var selectType = ""

    private var selectFileList = mutableListOf<MediaInfoBean>()

    private val FILE_CODE = 1002

    private var showType = AppConstant.Constant.NUM_MINUS_ONE

    @Inject
    lateinit var mainViewModel: MainViewModel

    override fun getLayout(): Int {
        return R.layout.act_upload
    }

    override fun initData() {
    }

    override fun initView() {

        icv_file_type.clicks().subscribe {
            XPopup.Builder(this).asBottomList("", fileTypeArray
            ) { position, text ->
                selectFileType = fileTypeArray[position]
                icv_file_type.rightContentFontSize = 15f
                icv_file_type.rightContent = selectFileType
                if (showType != position+1){
                    selectFileList.clear()
                }
                showType = position+1
            }.show()
        }
        val toTypedArray = mutableListOf<String>().apply {
            add("推荐")
            add("哈哈")

        }.toTypedArray()

        icv_type.clicks().subscribe {
            XPopup.Builder(this).asBottomList("", toTypedArray
            ) { position, text ->
                selectType = toTypedArray[position]
                icv_type.rightContentFontSize = 15f
                icv_type.rightContent = selectType
            }.show()
        }

        ll_image.clicks().subscribe {
            if (showType == AppConstant.Constant.NUM_MINUS_ONE){
                showShort("先选择文件类型")
                return@subscribe
            }
            buildARouter(AppConstant.RoutePath.PICTURE_SELECT_ACTIVITY)
                .withParcelableArrayList(AppConstant.Constant.DATA, selectFileList as ArrayList)
                .withInt(AppConstant.Constant.TYPE,showType)
                .withInt(AppConstant.Constant.NUM,AppConstant.Constant.NUM_ONE)
                .navigation(this,FILE_CODE)
        }
    }

    override fun initViewModel() {

        getMainComponent(this).inject(this)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data?.let {
            if (requestCode == FILE_CODE && resultCode == RESULT_OK){
                it.getParcelableArrayListExtra<MediaInfoBean>(AppConstant.Constant.DATA)?.let { beans ->
                    selectFileList = beans
                    if (selectFileList.size > 0){
                        if (showType == AppConstant.Constant.NUM_ONE){
                            Glide.with(this).load(selectFileList[0].filePath).centerCrop().error(R.drawable.iv_image_error)
                                .placeholder(R.drawable.iv_image_placeholder).into(siv_image)
                        }else{
                            Glide.with(this)
                                .setDefaultRequestOptions(RequestOptions().frame(1000))
                                .load(selectFileList[0].filePath)
                                .centerCrop()
                                .error(R.drawable.iv_image_error)
                                .placeholder(R.drawable.iv_image_placeholder)
                                .into(siv_image)
                        }

                    }else{
                        siv_image.setImageResource(0)
                    }
                }
            }
        }
    }
}