package com.yang.module_mine.ui.activity

import android.os.Environment
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yang.apt_annotation.annotain.InjectViewModel
import com.yang.lib_common.base.ui.activity.BaseActivity
import com.yang.lib_common.bus.event.UIChangeLiveData
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.proxy.InjectViewModelProxy
import com.yang.lib_common.util.buildARouter
import com.yang.lib_common.util.filterEmptyFile
import com.yang.lib_common.util.getFilePath
import com.yang.module_mine.R
import com.yang.module_mine.data.ViewHistoryData
import com.yang.module_mine.viewmodel.MineViewModel
import kotlinx.android.synthetic.main.view_normal_recyclerview.*

/**
 * @Author Administrator
 * @ClassName HistoryActivity
 * @Description 观看历史
 * @Date 2021/8/31 10:44
 */
@Route(path = AppConstant.RoutePath.VIEW_HISTORY_ACTIVITY)
class ViewHistoryActivity:BaseActivity() {

    @InjectViewModel
    lateinit var mineViewModel: MineViewModel

    private lateinit var mAdapter: MAdapter
    override fun getLayout(): Int {
        return R.layout.act_view_history
    }

    override fun initData() {
    }

    override fun initView() {
        initRecyclerView()
    }

    override fun initUIChangeLiveData(): UIChangeLiveData? {
        return mineViewModel.uC
    }

    override fun initViewModel() {
        InjectViewModelProxy.inject(this)
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        val mutableListOf = mutableListOf<ViewHistoryData>()
        mAdapter = MAdapter(R.layout.item_view_history, mutableListOf).apply {
            setOnItemClickListener { adapter, view, position ->
                val item = mAdapter.getItem(position)
                item?.let {
                    if (it.type == "1"){
                        buildARouter(AppConstant.RoutePath.PICTURE_ITEM_ACTIVITY)
                            .withString(AppConstant.Constant.ID, it.id)
                            .navigation()
                    }else{
                        buildARouter(AppConstant.RoutePath.VIDEO_ITEM_ACTIVITY)
                            .withString(AppConstant.Constant.URL, it.filePath)
                            .navigation()
                    }
                }

            }
        }
        recyclerView.adapter = mAdapter
        val picturePath = getFilePath().filterEmptyFile()

        picturePath.forEach {
            mutableListOf.add(ViewHistoryData("1",it))
        }
        val videoPath = getFilePath("${Environment.getExternalStorageDirectory()}/MFiles/video").filterEmptyFile()
        videoPath.forEach {
            mutableListOf.add(ViewHistoryData("2",it))
        }
        mAdapter.replaceData(mutableListOf)
    }

    inner class MAdapter(layoutResId: Int, list: MutableList<ViewHistoryData>) :
        BaseQuickAdapter<ViewHistoryData, BaseViewHolder>(layoutResId, list) {
        override fun convert(helper: BaseViewHolder, item: ViewHistoryData) {
            val ivImage = helper.getView<ImageView>(R.id.iv_image)
            Glide.with(ivImage).load(item.filePath)
                .error(R.drawable.iv_image_error)
                .placeholder(R.drawable.iv_image_placeholder).into(ivImage)
            if (item.type == "2"){
                helper.setText(R.id.tv_type,"#视频")
            }else{
                helper.setText(R.id.tv_type,"#图片")
            }
        }
    }
}