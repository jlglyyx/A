package com.yang.module_video.ui.activity

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yang.apt_annotation.annotain.InjectViewModel
import com.yang.lib_common.base.ui.activity.BaseActivity
import com.yang.lib_common.bus.event.UIChangeLiveData
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.proxy.InjectViewModelProxy
import com.yang.lib_common.room.entity.UploadTaskData
import com.yang.lib_common.upload.UploadListener
import com.yang.lib_common.upload.UploadManage
import com.yang.module_video.R
import com.yang.module_video.viewmodel.VideoViewModel
import kotlinx.android.synthetic.main.act_video_upload.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @Author Administrator
 * @ClassName UploadActivity
 * @Description
 * @Date 2021/11/19 11:08
 */
@Route(path = AppConstant.RoutePath.VIDEO_UPLOAD_TASK_ACTIVITY)
class VideoUploadTaskActivity : BaseActivity(), UploadListener {

    @InjectViewModel
    lateinit var videoViewModel: VideoViewModel


    private lateinit var videoUploadTaskAdapter: VideoUploadTaskAdapter


    override fun getLayout(): Int {
        return R.layout.act_video_upload_task
    }

    override fun initUIChangeLiveData(): UIChangeLiveData {
        return videoViewModel.uC
    }

    override fun initData() {
        UploadManage.instance.addUploadListener(this)
    }

    override fun initView() {
        initRecyclerView()
    }

    override fun initViewModel() {

        InjectViewModelProxy.inject(this)
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        videoUploadTaskAdapter =
            VideoUploadTaskAdapter(R.layout.item_upload_task, mutableListOf())
        recyclerView.adapter = videoUploadTaskAdapter
        videoUploadTaskAdapter.addData(
            UploadTaskData(
                "1",
                0,
                "/storage/emulated/0/MFiles/video/B/舞蹈/aaa.mp4"
            ,0)
        )
        videoUploadTaskAdapter.addData(
            UploadTaskData(
                "1",
                0,
                "/storage/emulated/0/MFiles/video/B/跳舞/aaa.mp4"
            ,0)
        )


        videoUploadTaskAdapter.setOnItemChildClickListener { adapter, view, position ->
            val item = videoUploadTaskAdapter.getItem(position)
            if (view.id == R.id.tv_progress) {
                when(item?.status){
                    0 ->{

                    }
                    1 ->{

                    }
                    2 ->{

                    }
                }

            }

        }

    }


    inner class VideoUploadTaskAdapter(layoutResId: Int, data: MutableList<UploadTaskData>?) :
        BaseQuickAdapter<UploadTaskData, BaseViewHolder>(layoutResId, data) {
        override fun convert(helper: BaseViewHolder, item: UploadTaskData) {
            helper.setText(R.id.tv_content, item.filePath)
                .setText(R.id.tv_progress, "${item.progress}")
                .addOnClickListener(R.id.tv_progress)
        }
    }

    override fun onProgress(noticeId: Int, progress: Int) {
        val item = videoUploadTaskAdapter.getItem(noticeId)
        item?.progress = progress
        if (recyclerView.isComputingLayout) {
            GlobalScope.launch(Dispatchers.Main) {
                delay(500)
                videoUploadTaskAdapter.notifyDataSetChanged()
            }
        }
        Log.i(TAG, "onProgress: $noticeId  $progress")
    }

    override fun onSuccess(noticeId: Int) {
    }

    override fun onFailed(noticeId: Int) {

    }

    override fun onDestroy() {
        super.onDestroy()
        UploadManage.instance.removeUploadListener(this)
    }
}