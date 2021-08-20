package com.yang.lib_common.dialog

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.yang.lib_common.R
import com.yang.lib_common.down.thread.MultiMoreThreadDownload
import com.lxj.xpopup.impl.FullScreenPopupView
import com.lxj.xpopup.photoview.PhotoView
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.wang.avi.AVLoadingIndicatorView
import kotlinx.android.synthetic.main.dialog_image_viewpager.view.*


class ImageViewPagerDialog : FullScreenPopupView {

    private lateinit var viewPager: ViewPager2

    private var data: MutableList<String>

    private var mContext: Context

    private var mDialog: ImageViewPagerDialog

    private var position: Int = 0

    private var gsyVideoPlayer: StandardGSYVideoPlayer? = null

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
        //val tvCount = findViewById<TextView>(R.id.tv_count)
        tv_count.text = "1/${data.size}"
        viewPager.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tv_count.text = "${position+1}/${data.size}"
            }
        })


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
                gsyVideoPlayer = holder.itemView.findViewById(R.id.detailPlayer)
                val avi = holder.itemView.findViewById<AVLoadingIndicatorView>(R.id.avi)
                val endsWith = data[position].endsWith(".mp4")
                if (endsWith){
                    avi.visibility = View.GONE
                    photoView.visibility = View.GONE
                    gsyVideoPlayer?.setUpLazy(data[position], true, null, null, "这是title")
                    gsyVideoPlayer?.titleTextView?.visibility = View.GONE
                    gsyVideoPlayer?.backButton?.visibility = View.GONE
                    gsyVideoPlayer?.fullscreenButton?.visibility = View.GONE

                    gsyVideoPlayer?.apply {
                        setIsTouchWiget(true)
                        isRotateViewAuto = false
                        isLockLand = false
                        isAutoFullWithSize = true
                        isShowFullAnimation = false
                        isNeedLockFull = true
                    }



                }else{
                    gsyVideoPlayer?.visibility = View.GONE

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
                    Glide.with(photoView)
                        .load(data[position])
                        .fitCenter()
                        .listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                avi.visibility = View.GONE
                                return false
                            }

                        })
                        .into(photoView)
                }

            }

        }
        viewPager.setCurrentItem(position, false)



    }

    override fun onDismiss() {
        imageViewPagerDialogCallBack?.getPosition(viewPager.currentItem)
        gsyVideoPlayer?.currentPlayer?.release()
        super.onDismiss()
    }


}