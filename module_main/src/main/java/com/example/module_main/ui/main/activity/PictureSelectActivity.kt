package com.example.module_main.ui.main.activity

import android.content.Intent
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lib_common.base.ui.activity.BaseActivity
import com.example.lib_common.data.MediaInfoBean
import com.example.lib_common.dialog.ImageViewPagerDialog
import com.example.lib_common.widget.CommonToolBar
import com.example.module_main.R
import com.example.module_main.adapter.PictureSelectAdapter
import com.lxj.xpopup.XPopup
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
class PictureSelectActivity : BaseActivity() {

    private lateinit var pictureSelectAdapter: PictureSelectAdapter

    private var data: MutableList<MediaInfoBean> = mutableListOf()

    override fun getLayout(): Int {
        return R.layout.act_picture_select
    }

    override fun initData() {
    }

    override fun initView() {
        commonToolBar.tVRightCallBack = object : CommonToolBar.TVRightCallBack {
            override fun tvRightClickListener(view: View) {
                val intent = Intent()
                intent.putStringArrayListExtra(
                    "Data",
                    data.map {
                        it.filePath
                    } as ArrayList<String>?
                )
                setResult(RESULT_OK, intent)
                finish()
            }

        }
        initRecyclerView()
        Log.i(TAG, "initView: ${61/60} ${61%60}")
    }

    override fun initViewModel() {

    }

    private fun initRecyclerView() {
        pictureSelectAdapter = PictureSelectAdapter(R.layout.item_picture_select,mutableListOf())
        recyclerView.adapter = pictureSelectAdapter
        recyclerView.layoutManager = GridLayoutManager(this, 3)

        pictureSelectAdapter.setOnItemClickListener { adapter, view, position ->
            val element = adapter.data[position] as MediaInfoBean
            var imageList =(adapter.data as MutableList<MediaInfoBean>).map {
                it.filePath
            } as MutableList<String>
            val imageViewPagerDialog =
                ImageViewPagerDialog(this, imageList , position)
            XPopup.Builder(this).asCustom(imageViewPagerDialog).show()
        }

        pictureSelectAdapter.setOnItemChildClickListener { adapter, view, position ->
            when(view.id){
                R.id.cb_image ->{
                    val element = adapter.data[position] as MediaInfoBean
                    if (element.isSelect){
                        element.isSelect = false
                        data.remove(element)
                    }else{
                        element.isSelect = true
                        data.add(element)
                    }
                }
            }

        }

        lifecycleScope.launch (Dispatchers.IO){
            val allPictureAndVideo = getAllPictureAndVideo()
            val allVideo = getAllVideo()
            val sortedByDescending = (allPictureAndVideo + allVideo).sortedByDescending {
                it.fileCreateTime
            }
            withContext(Dispatchers.Main){
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
        var i=0
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