package com.yang.module_main.ui.main.activity

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.lxj.xpopup.XPopup
import com.yang.lib_common.base.ui.activity.BaseActivity
import com.yang.lib_common.bus.event.UIChangeLiveData
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.data.MediaInfoBean
import com.yang.lib_common.dialog.ImageViewPagerDialog
import com.yang.lib_common.scope.ModelWithFactory
import com.yang.lib_common.util.*
import com.yang.lib_common.widget.CommonToolBar
import com.yang.module_main.R
import com.yang.module_main.adapter.PictureSelectAdapter
import com.yang.module_main.data.model.DynamicData
import com.yang.module_main.helper.getMainComponent
import com.yang.module_main.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.act_add_dynamic.*
import java.util.*
import javax.inject.Inject

@Route(path = AppConstant.RoutePath.ADD_DYNAMIC_ACTIVITY)
class AddDynamicActivity : BaseActivity() {

    @Inject
    @ModelWithFactory
    lateinit var mainViewModel: MainViewModel

    private lateinit var pictureSelectAdapter: PictureSelectAdapter

    private var data: MutableList<MediaInfoBean> = mutableListOf()

    private lateinit var imageView:ImageView


    override fun getLayout(): Int {
        return R.layout.act_add_dynamic
    }

    override fun initData() {
    }

    override fun initView() {
        commonToolBar.tVRightCallBack = object : CommonToolBar.TVRightCallBack {
            override fun tvRightClickListener() {
                if (pictureSelectAdapter.data.isEmpty()){
                    mainViewModel.requestSuccess()
                    return
                }
                uploadFile(pictureSelectAdapter.data)
            }
        }
        initRecyclerView()
    }

    private fun addDynamic() {
        val dynamicData = DynamicData()
        dynamicData.userId = getUserInfo()?.id
        dynamicData.content = et_dynamic.text.toString()
        dynamicData.imageUrls = mainViewModel.pictureListLiveData.value?.formatWithSymbol("#")
        mainViewModel.addDynamic(dynamicData)
    }

    private fun uploadFile(data: MutableList<MediaInfoBean>) {
        mainViewModel.uploadFile(data)
    }

    override fun initUIChangeLiveData(): UIChangeLiveData {
        return mainViewModel.uC
    }

    override fun initViewModel() {
        getMainComponent(this).inject(this)
        mainViewModel.pictureListLiveData.observe(this, Observer {
            addDynamic()
        })
    }



    private fun initRecyclerView() {
        pictureSelectAdapter = PictureSelectAdapter(R.layout.item_picture_select,data,false)
        recyclerView.adapter = pictureSelectAdapter
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        val registerForActivityResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK){
                    val mediaInfoBeans = it.data?.getParcelableArrayListExtra<MediaInfoBean>(AppConstant.Constant.DATA)
                    if (mediaInfoBeans?.size!! >= 9){
                            imageView.visibility = View.GONE
                    }else{
                        imageView.visibility = View.VISIBLE
                    }
                    pictureSelectAdapter.setNewData(mediaInfoBeans)
                }
            }
        imageView = ImageView(this).apply {
            setImageResource(R.drawable.iv_add)
            scaleType = ImageView.ScaleType.CENTER_CROP
            setBackgroundResource(android.R.color.darker_gray)
            val i =
                (getScreenPx(this@AddDynamicActivity)[0] - 20f.dip2px(this@AddDynamicActivity)) / 3
            layoutParams = ViewGroup.LayoutParams(i, i)
            setOnClickListener {
                registerForActivityResult.launch(Intent(this@AddDynamicActivity,PictureSelectActivity::class.java)
                    .putParcelableArrayListExtra(AppConstant.Constant.DATA, pictureSelectAdapter.data as ArrayList
                ))

            }
        }
        pictureSelectAdapter.addFooterView(imageView)
        pictureSelectAdapter.setOnItemLongClickListener { adapter, view, position ->
            pictureSelectAdapter.remove(position)
            if (!imageView.isVisible) {
                imageView.visibility = View.VISIBLE
            }
            return@setOnItemLongClickListener false
        }
        pictureSelectAdapter.setOnItemClickListener { adapter, view, position ->
            var imageList =(adapter.data as MutableList<MediaInfoBean>).map {
                it.filePath
            } as MutableList<String>
            val imageViewPagerDialog =
                ImageViewPagerDialog(this, imageList , position,false)
            XPopup.Builder(this).asCustom(imageViewPagerDialog).show()
        }
    }


}