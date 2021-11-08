package com.yang.module_mine.ui.activity

import android.text.TextUtils
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.yang.lib_common.base.ui.activity.BaseActivity
import com.yang.lib_common.bus.event.UIChangeLiveData
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.transform.BlurTransformation
import com.yang.lib_common.util.buildARouter
import com.yang.lib_common.util.getUserInfo
import com.yang.lib_common.widget.CommonToolBar
import com.yang.module_mine.R
import com.yang.module_mine.helper.getMineComponent
import com.yang.module_mine.viewmodel.MineViewModel
import kotlinx.android.synthetic.main.act_other_person_info.*
import javax.inject.Inject
/**
 * @Author Administrator
 * @ClassName OtherPersonInfoActivity
 * @Description 其他人信息
 * @Date 2021/9/10 10:51
 */
@Route(path = AppConstant.RoutePath.OTHER_PERSON_INFO_ACTIVITY)
class OtherPersonInfoActivity : BaseActivity() {
    @Inject
    lateinit var mineViewModel: MineViewModel

    override fun getLayout(): Int {
        return R.layout.act_other_person_info
    }

    override fun initData() {
        val id = intent.getStringExtra(AppConstant.Constant.ID)
        val userInfo = getUserInfo()

        if (!TextUtils.equals(id,userInfo?.id)){
            commonToolBar.rightContentVisible = false
        }

        Glide.with(this).load(userInfo?.userImage).error(R.drawable.iv_image_error)
            .placeholder(R.drawable.iv_image_placeholder).into(siv_img)
    }

    override fun initView() {
        commonToolBar.tVRightCallBack = object : CommonToolBar.TVRightCallBack{
            override fun tvRightClickListener() {
                buildARouter(AppConstant.RoutePath.CHANGE_USER_INFO_ACTIVITY).navigation()
            }

        }

        Glide.with(this)
            .load("https://scpic.chinaz.net/files/pic/pic9/202107/bpic23810.jpg")
            .apply(RequestOptions.bitmapTransform(BlurTransformation(this)))
            .error(R.drawable.iv_image_error)
            .placeholder(R.drawable.iv_image_placeholder)
            .into(iv_bg)
    }

    override fun initUIChangeLiveData(): UIChangeLiveData? {
        return mineViewModel.uC
    }

    override fun initViewModel() {
        getMineComponent(this).inject(this)
    }







}