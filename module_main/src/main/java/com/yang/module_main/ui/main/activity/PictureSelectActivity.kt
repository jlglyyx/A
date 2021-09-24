package com.yang.module_main.ui.main.activity

import android.content.Intent
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.lxj.xpopup.XPopup
import com.yang.lib_common.base.ui.activity.BaseActivity
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.data.MediaInfoBean
import com.yang.lib_common.dialog.ImageViewPagerDialog
import com.yang.lib_common.util.showShort
import com.yang.lib_common.widget.CommonToolBar
import com.yang.module_main.R
import com.yang.module_main.adapter.PictureSelectAdapter
import kotlinx.android.synthetic.main.act_add_dynamic.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


/**
 * @Author Administrator
 * @ClassName PictureSelectActivity
 * @Description
 * @Date 2021/8/6 11:08
 */
@Route(path = AppConstant.RoutePath.PICTURE_SELECT_ACTIVITY)
class PictureSelectActivity : BaseActivity() {

    private lateinit var pictureSelectAdapter: PictureSelectAdapter

    private var data: MutableList<MediaInfoBean> = mutableListOf()

    override fun getLayout(): Int {
        return R.layout.act_picture_select
    }

    override fun initData() {
        val selectData =
            intent.getParcelableArrayListExtra<MediaInfoBean>(AppConstant.Constant.DATA)
        selectData?.let {
            data.addAll(it)
        }
    }

    override fun initView() {
        commonToolBar.tVRightCallBack = object : CommonToolBar.TVRightCallBack {
            override fun tvRightClickListener() {
                val intent = Intent()
                intent.putParcelableArrayListExtra(AppConstant.Constant.DATA, data as ArrayList)
                setResult(RESULT_OK, intent)
                finish()
            }

        }
        initRecyclerView()
    }

    override fun initViewModel() {

    }

    private fun initRecyclerView() {
        //(recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        pictureSelectAdapter = PictureSelectAdapter(R.layout.item_picture_select, mutableListOf())
        recyclerView.adapter = pictureSelectAdapter
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        pictureSelectAdapter.setOnItemClickListener { adapter, view, position ->
            var imageList = (adapter.data as MutableList<MediaInfoBean>).map {
                it.filePath
            } as MutableList<String>
            val imageViewPagerDialog =
                ImageViewPagerDialog(this, imageList, position, false)
            XPopup.Builder(this).asCustom(imageViewPagerDialog).show()
        }
//        var lastPosition = -1
//        var downX = 0
//        var downY = 0
//        recyclerView.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
//            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
//                 when (e.action) {
//                    MotionEvent.ACTION_DOWN -> {
//                        downX = e.x.toInt()
//                        downY = e.x.toInt()
//                    }
//                    MotionEvent.ACTION_MOVE -> {
//                        val childViewUnder: View? = rv.findChildViewUnder(e.x, e.y)
//                        childViewUnder?.let {
//                            val position = rv.getChildLayoutPosition(it)
//                            Log.i(TAG, "onInterceptTouchEvent: $position------$lastPosition")
//                            val moveX = e.x
//                            var moveY = e.y
//                            val abs = abs(moveX - downX)
//                            val abs1 = abs(moveY - downY)
//                            Log.i(TAG, "onInterceptTouchEvent: $abs   $abs1")
//                            if (position == lastPosition||(abs < 50 && abs1 < 50)) {
//                                return false
//                            }
//                            val element = pictureSelectAdapter.data[position] as MediaInfoBean
//                            if (element.isSelect) {
//                                element.isSelect = false
//                                data.remove(element)
//                            } else {
//                                if (data.size < 9) {
//                                    element.isSelect = true
//                                    element.selectPosition = data.size + 1
//                                    data.add(element)
//                                } else {
//                                    showShort("已达到最大数量")
//                                }
//                            }
//                            lastPosition = position
//                            pictureSelectAdapter.notifyItemChanged(position, false)
//                        }
//                    }
//                    MotionEvent.ACTION_UP -> {
//                        lastPosition = -1
//                    }
//                }
//                return false
//            }
//
//            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
//
//            }
//
//            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
//
//            }
//
//        })

        pictureSelectAdapter.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.cl_cb -> {
                    val element = adapter.data[position] as MediaInfoBean
                    if (element.isSelect) {
                        element.isSelect = false
                        data.remove(element)
                    } else {
                        if (data.size < 9) {
                            element.isSelect = true
                            data.add(element)
                        } else {
                            showShort("已达到最大数量")
                            return@setOnItemChildClickListener
                        }
                    }


                    for (a in data.withIndex()) {
                        for (b in (adapter.data as MutableList<MediaInfoBean>).withIndex()) {
                            if (TextUtils.equals(a.value.filePath, b.value.filePath)) {
                                b.value.selectPosition = a.index + 1
                                break
                            }
                        }
                    }
                    //adapter.notifyItemChanged(position, false)
                    adapter.notifyDataSetChanged()
                }
            }

        }


        lifecycleScope.launch(Dispatchers.IO) {
            val allPictureAndVideo = getAllPictureAndVideo()
            val allVideo = getAllVideo()
            val sortedByDescending = (allPictureAndVideo + allVideo).sortedByDescending {
                it.fileCreateTime
            }.apply {
                for (i in data) {
                    this.findLast {
                        TextUtils.equals(i.filePath, it.filePath)
                    }.apply {
                        this?.isSelect = true
                        this?.selectPosition = i.selectPosition
                    }
                }
            }
            withContext(Dispatchers.Main) {
                pictureSelectAdapter.setNewData(sortedByDescending)
            }
        }
    }

    private fun getAllPictureAndVideo(): MutableList<MediaInfoBean> {
        val mutableListOf = mutableListOf<MediaInfoBean>()
        val query = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            MediaStore.Images.Media.DATE_ADDED + " DESC "
        )
        var i = 0
        while (query?.moveToNext()!!) {
            val mediaInfoBean = MediaInfoBean()
            var path = query.getString(query.getColumnIndex(MediaStore.Images.Media.DATA))
            val duration = query.getString(query.getColumnIndex(MediaStore.Images.Media.DURATION))
            val size = query.getString(query.getColumnIndex(MediaStore.Images.Media.SIZE))
            val name = query.getString(query.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME))
            val createTime = query.getLong(query.getColumnIndex(MediaStore.Images.Media.DATE_ADDED))
            mediaInfoBean.filePath = path
            mediaInfoBean.fileDurationTime = duration
            mediaInfoBean.fileSize = size
            mediaInfoBean.fileName = name
            mediaInfoBean.fileCreateTime = createTime
            mediaInfoBean.fileType = 0
            mutableListOf.add(mediaInfoBean)

            Log.i(TAG, "getAllPictureAndVideo:${i++}=== $path  ")
        }
        return mutableListOf
    }

    private fun getAllVideo(): MutableList<MediaInfoBean> {
        val mutableListOf = mutableListOf<MediaInfoBean>()
        val query = contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            MediaStore.Video.Media.DATE_ADDED + " DESC "
        )

        while (query?.moveToNext()!!) {
            val mediaInfoBean = MediaInfoBean()
            var path = query.getString(query.getColumnIndex(MediaStore.Video.Media.DATA))
            val duration = query.getString(query.getColumnIndex(MediaStore.Video.Media.DURATION))
            val size = query.getString(query.getColumnIndex(MediaStore.Video.Media.SIZE))
            val name = query.getString(query.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME))
            val createTime = query.getLong(query.getColumnIndex(MediaStore.Video.Media.DATE_ADDED))
            mediaInfoBean.filePath = path
            mediaInfoBean.fileDurationTime = duration
            mediaInfoBean.fileSize = size
            mediaInfoBean.fileName = name
            mediaInfoBean.fileCreateTime = createTime
            mediaInfoBean.fileType = 1
            mutableListOf.add(mediaInfoBean)
            Log.i(TAG, "getAllVideo: $path  ${mediaInfoBean.toString()}")
        }
        return mutableListOf
    }
}