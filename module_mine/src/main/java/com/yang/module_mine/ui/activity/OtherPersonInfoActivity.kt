package com.yang.module_mine.ui.activity

import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.yang.lib_common.base.ui.activity.BaseActivity
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.transform.BlurTransformation
import com.yang.lib_common.util.buildARouter
import com.yang.lib_common.util.getUserInfo
import com.yang.lib_common.widget.CommonToolBar
import com.yang.module_mine.R
import kotlinx.android.synthetic.main.act_other_person_info.*

@Route(path = AppConstant.RoutePath.OTHER_PERSON_INFO_ACTIVITY)
class OtherPersonInfoActivity : BaseActivity() {

    private var alphaPercent = 0f

    private lateinit var tvCenterContent:TextView



    override fun getLayout(): Int {
        return R.layout.act_other_person_info
    }

    override fun initData() {
        val id = intent.getStringExtra(AppConstant.Constant.ID)
        val userInfo = getUserInfo()
        Glide.with(this).load(userInfo?.userImage).into(siv_img)
    }

    override fun initView() {
        tvCenterContent = commonToolBar.findViewById(R.id.tv_centerContent)
        commonToolBar.tVRightCallBack = object : CommonToolBar.TVRightCallBack{
            override fun tvRightClickListener() {
                buildARouter(AppConstant.RoutePath.CHANGE_USER_INFO_ACTIVITY).navigation()
            }

        }

        Glide.with(this)
            .load("https://img1.baidu.com/it/u=584562345,1740343441&fm=26&fmt=auto&gp=0.jpg")
            .apply(RequestOptions.bitmapTransform(BlurTransformation(this)))
            .into(iv_bg)
        Glide.with(this)
            .load("https://img1.baidu.com/it/u=584562345,1740343441&fm=26&fmt=auto&gp=0.jpg")
            .into(siv_img)
    }

    override fun initViewModel() {

    }







}