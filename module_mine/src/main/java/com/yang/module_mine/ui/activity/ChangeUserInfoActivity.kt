package com.yang.module_mine.ui.activity

import android.app.Activity
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.lxj.xpopup.XPopup
import com.yang.lib_common.base.ui.activity.BaseActivity
import com.yang.lib_common.bus.event.UIChangeLiveData
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.data.UserInfoData
import com.yang.lib_common.util.buildARouter
import com.yang.lib_common.util.clicks
import com.yang.lib_common.util.getUserInfo
import com.yang.lib_common.util.uri2path
import com.yang.lib_common.widget.CommonToolBar
import com.yang.module_mine.R
import com.yang.module_mine.adapter.ActivityInfoAdapter
import com.yang.module_mine.data.ActivityInfoData
import com.yang.module_mine.helper.getMineComponent
import com.yang.module_mine.viewmodel.MineViewModel
import kotlinx.android.synthetic.main.act_change_user_info.*
import kotlinx.android.synthetic.main.act_other_person_info.commonToolBar
import javax.inject.Inject


/**
 * @Author Administrator
 * @ClassName ChangeUserInfoActivity
 * @Description 修改信息
 * @Date 2021/8/27 14:53
 */
@Route(path = AppConstant.RoutePath.CHANGE_USER_INFO_ACTIVITY)
class ChangeUserInfoActivity : BaseActivity() {

    @Inject
    lateinit var mineViewModel: MineViewModel

    private lateinit var activityInfoAdapter: ActivityInfoAdapter

    private var imageUrl = ""

    private var selectSex = ""

    private var sexArray = arrayOf("男","女")

    private var url :String? = null

    override fun getLayout(): Int {
        return R.layout.act_change_user_info
    }

    override fun initData() {
        initRecyclerView()
        val userInfo = getUserInfo()
        Glide.with(this).load(userInfo?.userImage).error(R.drawable.iv_image_error)
            .placeholder(R.drawable.iv_image_placeholder).into(siv_image)

        mineViewModel.mUserInfoData.observe(this, Observer {
            finish()
        })
        mineViewModel.pictureListLiveData.observe(this, Observer {
            url = it[0]
        })

    }

    override fun initView() {
        commonToolBar.tVRightCallBack = object : CommonToolBar.TVRightCallBack {
            override fun tvRightClickListener() {
                val userInfoData = UserInfoData(
                    null,
                    null,
                    et_name.text.toString(),
                    null,
                    null,
                    null,
                    url,
                    0,
                    null,
                    null,
                    null
                )
                mineViewModel.changeUserInfo(userInfoData)
            }

        }

        ll_sex.clicks().subscribe {
            XPopup.Builder(this).asBottomList("", sexArray
            ) { position, text ->
                selectSex = sexArray[position]
                tv_sex.text = selectSex
            }.show()
        }
        val registerForActivityResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK && null != it.data) {
                    it.data?.data?.let { uri ->
                        imageUrl = uri2path(this, uri)
                    }
                    mineViewModel.uploadFile(mutableListOf(imageUrl))
                    Glide.with(this).load(imageUrl).centerCrop().error(R.drawable.iv_image_error)
                        .placeholder(R.drawable.iv_image_placeholder).into(siv_image)
                }
            }
        ll_image.clicks().subscribe {
            val innerIntent = Intent(Intent.ACTION_PICK)
            innerIntent.type = "image/*"
            val wrapperIntent = Intent.createChooser(innerIntent, "选择头像")
            registerForActivityResult.launch(wrapperIntent)
        }
    }

    override fun initUIChangeLiveData(): UIChangeLiveData {
        return mineViewModel.uC
    }

    override fun initViewModel() {
        getMineComponent(this).inject(this)
    }

    private fun initRecyclerView() {
        activityInfoAdapter = ActivityInfoAdapter(
            R.layout.item_activity_info,
            mutableListOf<ActivityInfoData>().apply {
                add(ActivityInfoData("头像", AppConstant.RoutePath.ADD_DYNAMIC_ACTIVITY))
                add(ActivityInfoData("签名", AppConstant.RoutePath.ADD_DYNAMIC_ACTIVITY))
                add(ActivityInfoData("昵称", AppConstant.RoutePath.ADD_DYNAMIC_ACTIVITY))
                add(ActivityInfoData("性别", AppConstant.RoutePath.ADD_DYNAMIC_ACTIVITY))
                add(ActivityInfoData("职业", AppConstant.RoutePath.ADD_DYNAMIC_ACTIVITY))
                add(ActivityInfoData("公司", AppConstant.RoutePath.ADD_DYNAMIC_ACTIVITY))
            })
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = activityInfoAdapter

        activityInfoAdapter.setOnItemClickListener { adapter, view, position ->
            val item = adapter.getItem(position) as ActivityInfoData
            buildARouter(item.value).navigation()
        }
    }
}