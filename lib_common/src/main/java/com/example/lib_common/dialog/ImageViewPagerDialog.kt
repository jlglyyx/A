package com.example.lib_common.dialog

import android.content.Context
import android.os.Environment
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.lib_common.R
import com.example.lib_common.down.thread.MultiMoreThreadDownload
import com.lxj.xpopup.impl.FullScreenPopupView
import com.lxj.xpopup.photoview.PhotoView

class ImageViewPagerDialog : FullScreenPopupView {

    private lateinit var viewPager: ViewPager2

    private var data: MutableList<String>

    private var mContext: Context

    private var mDialog: ImageViewPagerDialog

    private var position: Int = 0

    var imageViewPagerDialogCallBack: ImageViewPagerDialogCallBack? = null

    interface ImageViewPagerDialogCallBack {
        fun getPosition(position: Int)
    }


    constructor(context: Context, data: MutableList<String>, position: Int) : super(context) {
        this.data = data
        this.mContext = context
        this.mDialog = this
        this.position = position
    }

    override fun getImplLayoutId(): Int {
        return R.layout.dialog_image_viewpager
    }

    override fun onCreate() {
        super.onCreate()
        viewPager = findViewById(R.id.viewPager)
        viewPager.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): RecyclerView.ViewHolder {
                val inflate = LayoutInflater.from(mContext)
                    .inflate(R.layout.dialog_image_viewpager_item, parent, false)
                return object : RecyclerView.ViewHolder(inflate) {

                }

            }

            override fun getItemCount(): Int {
                return data.size
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                val photoView = holder.itemView.findViewById<PhotoView>(R.id.photoView)
                photoView.setOnClickListener {
                    imageViewPagerDialogCallBack?.getPosition(position)
                    mDialog.dismiss()
                }


                photoView.setOnLongClickListener {
                    MultiMoreThreadDownload.Builder(mContext)
                        .parentFilePath("${Environment.getExternalStorageDirectory()}/MFiles/picture")
                        .filePath("${System.currentTimeMillis()}.jpg")
                        .fileUrl(data[position])
                        .build()
                        .start()

                    false
                }
                Glide.with(photoView).load(data[position]).into(photoView)

            }

        }
        viewPager.setCurrentItem(position, false)

    }


}