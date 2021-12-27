package com.yang.module_mine.ui.activity

import android.text.TextUtils
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.yang.apt_annotation.annotain.InjectViewModel
import com.yang.lib_common.base.ui.activity.BaseActivity
import com.yang.lib_common.bus.event.UIChangeLiveData
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.proxy.InjectViewModelProxy
import com.yang.lib_common.util.buildARouter
import com.yang.lib_common.util.getUserInfo
import com.yang.lib_common.widget.CommonToolBar
import com.yang.module_mine.R
import com.yang.module_mine.viewmodel.MineViewModel
import kotlinx.android.synthetic.main.act_other_person_info.*
/**
 * @Author Administrator
 * @ClassName OtherPersonInfoActivity
 * @Description 其他人信息
 * @Date 2021/9/10 10:51
 */
@Route(path = AppConstant.RoutePath.OTHER_PERSON_INFO_ACTIVITY)
class OtherPersonInfoActivity : BaseActivity() {
    @InjectViewModel
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

        Glide.with(this).load(userInfo?.userImage?:"https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fup.enterdesk.com%2Fedpic%2F39%2Fb7%2F53%2F39b75357f98675e2d6d5dcde1fb805a3.jpg&refer=http%3A%2F%2Fup.enterdesk.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1642840086&t=2a7574a5d8ecc96669ac3e050fe4fd8e").error(R.drawable.iv_image_error)
            .placeholder(R.drawable.iv_image_placeholder).into(siv_img)
    }

    override fun initView() {
        commonToolBar.tVRightCallBack = object : CommonToolBar.TVRightCallBack{
            override fun tvRightClickListener() {
                buildARouter(AppConstant.RoutePath.CHANGE_USER_INFO_ACTIVITY).navigation()
            }
        }
        iv_bg.imageUrl = "https://img1.baidu.com/it/u=1251916380,3661111139&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=800"
        lifecycle.addObserver(iv_bg)
    }

    override fun initUIChangeLiveData(): UIChangeLiveData? {
        return mineViewModel.uC
    }

    override fun initViewModel() {
        InjectViewModelProxy.inject(this)
    }







}