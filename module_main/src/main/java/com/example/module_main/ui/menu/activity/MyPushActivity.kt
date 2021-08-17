package com.example.module_main.ui.menu.activity

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.lib_common.base.ui.activity.BaseActivity
import com.example.lib_common.bus.event.UIChangeLiveData
import com.example.lib_common.constant.AppConstant
import com.example.lib_common.dialog.ImageViewPagerDialog
import com.example.lib_common.scope.ModelWithFactory
import com.example.lib_common.util.buildARouter
import com.example.lib_common.widget.GridNinePictureView
import com.example.module_main.R
import com.example.module_main.data.model.MainData
import com.example.module_main.helper.getMainComponent
import com.example.module_main.viewmodel.MainViewModel
import com.google.android.material.imageview.ShapeableImageView
import com.lxj.xpopup.XPopup
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import kotlinx.android.synthetic.main.view_normal_recyclerview.*
import javax.inject.Inject

@Route(path = AppConstant.RoutePath.MY_PUSH_ACTIVITY)
class MyPushActivity : BaseActivity(), OnRefreshLoadMoreListener {

    @Inject
    @ModelWithFactory
    lateinit var mainViewModel: MainViewModel

    private lateinit var mAdapter: MAdapter

    private var pageNum = 1

    override fun getLayout(): Int {
        return R.layout.act_my_push
    }

    override fun initData() {
        smartRefreshLayout.autoRefresh()

    }

    override fun initView() {
        initRecyclerView()
    }

    override fun initUIChangeLiveData(): UIChangeLiveData? {
        return mainViewModel.uC
    }

    override fun initViewModel() {
        getMainComponent(this).inject(this)

    }


    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        val mutableListOf = mutableListOf<MainData>().apply {

            add(MainData(AppConstant.Constant.ITEM_MAIN_TITLE).apply {
                userImage =
                    "https://img2.baidu.com/it/u=1801164193,3602394305&fm=26&fmt=auto&gp=0.jpg"
            })
            add(MainData(AppConstant.Constant.ITEM_MAIN_CONTENT_TEXT).apply {
                dynamicContent = "今天天气真好"
            })
            add(MainData(AppConstant.Constant.ITEM_MAIN_CONTENT_IMAGE).apply {
                imageList = mutableListOf<String>().apply {
                    add("https://scpic.chinaz.net/files/pic/pic9/202106/apic33102.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202106/apic33150.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32309.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32186.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32309.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32186.jpg")

                }
            })
            add(MainData(AppConstant.Constant.ITEM_MAIN_CONTENT_VIDEO).apply {
                videoUrl =
                    "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4"
            })
            add(MainData(AppConstant.Constant.ITEM_MAIN_IDENTIFICATION))
            add(MainData(AppConstant.Constant.ITEM_MAIN_TITLE).apply {
                userImage =
                    "https://img2.baidu.com/it/u=1801164193,3602394305&fm=26&fmt=auto&gp=0.jpg"
            })
            add(MainData(AppConstant.Constant.ITEM_MAIN_CONTENT_TEXT).apply {
                dynamicContent = "不知道吃什么就很烦 在线求解"
            })
            add(MainData(AppConstant.Constant.ITEM_MAIN_IDENTIFICATION))

            add(MainData(AppConstant.Constant.ITEM_MAIN_TITLE).apply {
                userImage =
                    "https://img2.baidu.com/it/u=1801164193,3602394305&fm=26&fmt=auto&gp=0.jpg"
            })
            add(MainData(AppConstant.Constant.ITEM_MAIN_CONTENT_IMAGE).apply {
                imageList = mutableListOf<String>().apply {
                    add("https://scpic.chinaz.net/files/pic/pic9/202106/apic33102.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202106/apic33150.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32309.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32186.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32309.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32186.jpg")

                }
            })
            add(MainData(AppConstant.Constant.ITEM_MAIN_IDENTIFICATION))

            add(MainData(AppConstant.Constant.ITEM_MAIN_TITLE).apply {
                userImage =
                    "https://img2.baidu.com/it/u=1801164193,3602394305&fm=26&fmt=auto&gp=0.jpg"
            })
            add(MainData(AppConstant.Constant.ITEM_MAIN_CONTENT_VIDEO).apply {
                videoUrl =
                    "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4"
            })
            add(MainData(AppConstant.Constant.ITEM_MAIN_IDENTIFICATION))
            add(MainData(AppConstant.Constant.ITEM_MAIN_TITLE).apply {
                userImage =
                    "https://img2.baidu.com/it/u=1801164193,3602394305&fm=26&fmt=auto&gp=0.jpg"
            })
            add(MainData(AppConstant.Constant.ITEM_MAIN_CONTENT_VIDEO).apply {
                videoUrl =
                    "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4"
            })
            add(MainData(AppConstant.Constant.ITEM_MAIN_IDENTIFICATION))
            add(MainData(AppConstant.Constant.ITEM_MAIN_TITLE).apply {
                userImage =
                    "https://img2.baidu.com/it/u=1801164193,3602394305&fm=26&fmt=auto&gp=0.jpg"
            })
            add(MainData(AppConstant.Constant.ITEM_MAIN_CONTENT_VIDEO).apply {
                videoUrl =
                    "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4"
            })
            add(MainData(AppConstant.Constant.ITEM_MAIN_IDENTIFICATION))
            add(MainData(AppConstant.Constant.ITEM_MAIN_TITLE).apply {
                userImage =
                    "https://img2.baidu.com/it/u=1801164193,3602394305&fm=26&fmt=auto&gp=0.jpg"
            })
            add(MainData(AppConstant.Constant.ITEM_MAIN_CONTENT_VIDEO).apply {
                videoUrl =
                    "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4"
            })
            add(MainData(AppConstant.Constant.ITEM_MAIN_IDENTIFICATION))
            add(MainData(AppConstant.Constant.ITEM_MAIN_TITLE).apply {
                userImage =
                    "https://img2.baidu.com/it/u=1801164193,3602394305&fm=26&fmt=auto&gp=0.jpg"
            })
            add(MainData(AppConstant.Constant.ITEM_MAIN_CONTENT_VIDEO).apply {
                videoUrl =
                    "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4"
            })
            add(MainData(AppConstant.Constant.ITEM_MAIN_IDENTIFICATION))
            add(MainData(AppConstant.Constant.ITEM_MAIN_TITLE).apply {
                userImage =
                    "https://img2.baidu.com/it/u=1801164193,3602394305&fm=26&fmt=auto&gp=0.jpg"
            })
            add(MainData(AppConstant.Constant.ITEM_MAIN_CONTENT_VIDEO).apply {
                videoUrl =
                    "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4"
            })
            add(MainData(AppConstant.Constant.ITEM_MAIN_IDENTIFICATION))
            add(MainData(AppConstant.Constant.ITEM_MAIN_TITLE).apply {
                userImage =
                    "https://img2.baidu.com/it/u=1801164193,3602394305&fm=26&fmt=auto&gp=0.jpg"
            })
            add(MainData(AppConstant.Constant.ITEM_MAIN_CONTENT_VIDEO).apply {
                videoUrl =
                    "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4"
            })
            add(MainData(AppConstant.Constant.ITEM_MAIN_IDENTIFICATION))
            add(MainData(AppConstant.Constant.ITEM_MAIN_TITLE).apply {
                userImage =
                    "https://img2.baidu.com/it/u=1801164193,3602394305&fm=26&fmt=auto&gp=0.jpg"
            })
            add(MainData(AppConstant.Constant.ITEM_MAIN_CONTENT_VIDEO).apply {
                videoUrl =
                    "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4"
            })
            add(MainData(AppConstant.Constant.ITEM_MAIN_IDENTIFICATION))
            add(MainData(AppConstant.Constant.ITEM_MAIN_TITLE).apply {
                userImage =
                    "https://img2.baidu.com/it/u=1801164193,3602394305&fm=26&fmt=auto&gp=0.jpg"
            })
            add(MainData(AppConstant.Constant.ITEM_MAIN_CONTENT_VIDEO).apply {
                videoUrl =
                    "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4"
            })
            add(MainData(AppConstant.Constant.ITEM_MAIN_IDENTIFICATION))
            add(MainData(AppConstant.Constant.ITEM_MAIN_TITLE).apply {
                userImage =
                    "https://img2.baidu.com/it/u=1801164193,3602394305&fm=26&fmt=auto&gp=0.jpg"
            })
            add(MainData(AppConstant.Constant.ITEM_MAIN_CONTENT_VIDEO).apply {
                videoUrl =
                    "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4"
            })




            add(MainData(AppConstant.Constant.ITEM_MAIN_IDENTIFICATION))
            add(MainData(AppConstant.Constant.ITEM_MAIN_TITLE).apply {
                userImage =
                    "https://img2.baidu.com/it/u=1801164193,3602394305&fm=26&fmt=auto&gp=0.jpg"
            })
            add(MainData(AppConstant.Constant.ITEM_MAIN_CONTENT_TEXT))
            add(MainData(AppConstant.Constant.ITEM_MAIN_CONTENT_IMAGE).apply {
                imageList = mutableListOf<String>().apply {
                    add("https://scpic.chinaz.net/files/pic/pic9/202106/apic33102.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202106/apic33150.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32309.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32186.jpg")
                    add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1336119765,2231343437&fm=26&gp=0.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32184.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32185.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32189.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32188.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32187.jpg")
                }
            })
            add(MainData(AppConstant.Constant.ITEM_MAIN_IDENTIFICATION))
            add(MainData(AppConstant.Constant.ITEM_MAIN_TITLE).apply {
                userImage =
                    "https://img2.baidu.com/it/u=1801164193,3602394305&fm=26&fmt=auto&gp=0.jpg"
            })
            add(MainData(AppConstant.Constant.ITEM_MAIN_CONTENT_VIDEO).apply {
                videoUrl =
                    "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4"
            })
            add(MainData(AppConstant.Constant.ITEM_MAIN_IDENTIFICATION))
            add(MainData(AppConstant.Constant.ITEM_MAIN_TITLE).apply {
                userImage =
                    "https://img2.baidu.com/it/u=1801164193,3602394305&fm=26&fmt=auto&gp=0.jpg"
            })
            add(MainData(AppConstant.Constant.ITEM_MAIN_CONTENT_IMAGE).apply {
                imageList = mutableListOf<String>().apply {
                    add("https://scpic.chinaz.net/files/pic/pic9/202106/apic33102.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202106/apic33150.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32309.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32186.jpg")
                }
            })
            add(MainData(AppConstant.Constant.ITEM_MAIN_IDENTIFICATION))

            add(MainData(AppConstant.Constant.ITEM_MAIN_TITLE).apply {
                userImage =
                    "https://img2.baidu.com/it/u=1801164193,3602394305&fm=26&fmt=auto&gp=0.jpg"
            })
            add(MainData(AppConstant.Constant.ITEM_MAIN_CONTENT_TEXT))
            add(MainData(AppConstant.Constant.ITEM_MAIN_CONTENT_IMAGE).apply {
                imageList = mutableListOf<String>().apply {
                    add("https://scpic.chinaz.net/files/pic/pic9/202106/apic33102.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32309.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32309.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32186.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32186.jpg")
                    add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1336119765,2231343437&fm=26&gp=0.jpg")
                }
            })

            add(MainData(AppConstant.Constant.ITEM_MAIN_TITLE))
            add(MainData(AppConstant.Constant.ITEM_MAIN_CONTENT_VIDEO).apply {
                videoUrl =
                    "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4"
            })
            add(MainData(AppConstant.Constant.ITEM_MAIN_IDENTIFICATION))
            add(MainData(AppConstant.Constant.ITEM_MAIN_TITLE))
            add(MainData(AppConstant.Constant.ITEM_MAIN_CONTENT_IMAGE).apply {
                imageList = mutableListOf<String>().apply {
                    add("https://scpic.chinaz.net/files/pic/pic9/202106/apic33102.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202106/apic33150.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32309.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32186.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32186.jpg")
                    add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1336119765,2231343437&fm=26&gp=0.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32184.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32185.jpg")
                }
            })
            add(MainData(AppConstant.Constant.ITEM_MAIN_IDENTIFICATION))

            add(MainData(AppConstant.Constant.ITEM_MAIN_TITLE))
            add(MainData(AppConstant.Constant.ITEM_MAIN_CONTENT_TEXT))
            add(MainData(AppConstant.Constant.ITEM_MAIN_CONTENT_IMAGE).apply {
                imageList = mutableListOf<String>().apply {
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32188.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32309.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32186.jpg")
                    add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1336119765,2231343437&fm=26&gp=0.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32184.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32185.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202106/apic33102.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202106/apic33150.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32189.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32187.jpg")
                }
            })
            add(MainData(AppConstant.Constant.ITEM_MAIN_IDENTIFICATION))

            add(MainData(AppConstant.Constant.ITEM_MAIN_TITLE))
            add(MainData(AppConstant.Constant.ITEM_MAIN_CONTENT_VIDEO).apply {
                videoUrl =
                    "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4"
            })
            add(MainData(AppConstant.Constant.ITEM_MAIN_IDENTIFICATION))
        }
        mAdapter = MAdapter(mutableListOf).apply {
            setOnItemChildClickListener { adapter, view, position ->
                when (view.id) {
                    R.id.siv_img -> {
                        buildARouter(AppConstant.RoutePath.OTHER_PERSON_INFO_ACTIVITY)
                            .navigation()
                    }

                }
            }
        }
        recyclerView.adapter = mAdapter


        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val firstVisibleItem =
                    linearLayoutManager.findFirstVisibleItemPosition()
                val lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                //大于0说明有播放
                if (GSYVideoManager.instance().playPosition >= 0) {
                    //当前播放的位置
                    val position = GSYVideoManager.instance().playPosition
                    //对应的播放列表TAG
                    if (GSYVideoManager.instance()
                            .playTag == TAG && (position < firstVisibleItem || position > lastVisibleItem)
                    ) {
                        if (GSYVideoManager.isFullState(this@MyPushActivity)) {
                            return
                        }
                        //如果滑出去了上面和下面就是否，和今日头条一样
                        GSYVideoManager.releaseAllVideos()
                        mAdapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }

    inner class MAdapter(list: MutableList<MainData>) :
        BaseMultiItemQuickAdapter<MainData, BaseViewHolder>(list) {

        init {
            addItemType(AppConstant.Constant.ITEM_MAIN_TITLE, R.layout.item_main_title)
            addItemType(
                AppConstant.Constant.ITEM_MAIN_CONTENT_TEXT,
                R.layout.item_main_content_text
            )
            addItemType(
                AppConstant.Constant.ITEM_MAIN_CONTENT_IMAGE,
                R.layout.item_main_content_image
            )
            addItemType(
                AppConstant.Constant.ITEM_MAIN_IDENTIFICATION,
                R.layout.item_main_identification
            )
            addItemType(
                AppConstant.Constant.ITEM_MAIN_CONTENT_VIDEO,
                R.layout.item_main_content_video
            )
        }

        override fun convert(helper: BaseViewHolder, item: MainData) {
            when (item.itemType) {
                AppConstant.Constant.ITEM_MAIN_TITLE -> {
                    val sivImg = helper.getView<ShapeableImageView>(R.id.siv_img)
                    helper.addOnClickListener(R.id.siv_img)
                    helper.setText(R.id.tv_time, item.createTime)
                    Glide.with(sivImg).load(item.userImage).into(sivImg)
                }
                AppConstant.Constant.ITEM_MAIN_CONTENT_TEXT -> {
                    helper.setText(R.id.tv_text, item.dynamicContent)
                }
                AppConstant.Constant.ITEM_MAIN_CONTENT_IMAGE -> {

                    val gridNinePictureView =
                        helper.getView<GridNinePictureView>(R.id.gridNinePictureView)
                    gridNinePictureView.data = item.imageList!!
                    gridNinePictureView.imageCallback = object : GridNinePictureView.ImageCallback {
                        override fun imageClickListener(position: Int) {
                            val imageViewPagerDialog =
                                ImageViewPagerDialog(this@MyPushActivity, item.imageList!!, position)
                            XPopup.Builder(this@MyPushActivity).asCustom(imageViewPagerDialog).show()
                        }
                    }
                }
                AppConstant.Constant.ITEM_MAIN_IDENTIFICATION -> {



                }
                AppConstant.Constant.ITEM_MAIN_CONTENT_VIDEO -> {
                    var gsyVideoPlayer = helper.getView<StandardGSYVideoPlayer>(R.id.detailPlayer)

                    gsyVideoPlayer.thumbImageView = ImageView(this@MyPushActivity).apply {
                        scaleType = ImageView.ScaleType.CENTER_CROP
                        setImageResource(R.drawable.iv_bear)
                    }
                    gsyVideoPlayer.setUpLazy(item.videoUrl, true, null, null, "这是title")
                    gsyVideoPlayer.titleTextView.visibility = View.GONE
                    gsyVideoPlayer.backButton.visibility = View.GONE
                    gsyVideoPlayer.fullscreenButton
                        .setOnClickListener {
                            gsyVideoPlayer.startWindowFullscreen(
                                this@MyPushActivity,
                                false,
                                true
                            )
                        }
                    gsyVideoPlayer.playTag = TAG
                    gsyVideoPlayer.playPosition = helper.adapterPosition
                    gsyVideoPlayer.isAutoFullWithSize = true
                    gsyVideoPlayer.isReleaseWhenLossAudio = false
                    gsyVideoPlayer.isShowFullAnimation = true
                    gsyVideoPlayer.setIsTouchWiget(false)
                }
            }
        }
    }
    override fun onLoadMore(refreshLayout: RefreshLayout) {
        pageNum++
        mainViewModel.getDynamicList( "", pageNum)
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        pageNum = 1
        mainViewModel.getDynamicList( "", pageNum)
    }

    override fun onPause() {
        super.onPause()
        GSYVideoManager.onPause()
    }

    override fun onResume() {
        super.onResume()
        GSYVideoManager.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
    }
}