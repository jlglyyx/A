package com.example.module_main.ui.main.fragment

import android.content.Intent
import android.view.View
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.lib_common.base.ui.fragment.BaseLazyFragment
import com.example.lib_common.bus.event.UIChangeLiveData
import com.example.lib_common.constant.AppConstant
import com.example.lib_common.dialog.ImageViewPagerDialog
import com.example.lib_common.help.buildARouter
import com.example.lib_common.util.dip2px
import com.example.lib_common.util.getUserInfo
import com.example.lib_common.widget.CommonToolBar
import com.example.lib_common.widget.GridNinePictureView
import com.example.module_main.R
import com.example.module_main.data.model.MainData
import com.example.module_main.di.factory.MainViewModelFactory
import com.example.module_main.helper.getMainComponent
import com.example.module_main.ui.main.activity.AddDynamicActivity
import com.example.module_main.ui.main.activity.MainActivity
import com.example.module_main.viewmodel.MainViewModel
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.ShapeAppearanceModel
import com.lxj.xpopup.XPopup
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fra_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject


@Route(path = AppConstant.RoutePath.MAIN_FRAGMENT)
class MainFragment : BaseLazyFragment() {

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    private lateinit var mainViewModel: MainViewModel

    private lateinit var mAdapter: MAdapter

    var mutableListOf: MutableList<MainData> = mutableListOf()

    override fun getLayout(): Int {
        return R.layout.fra_main
    }

    override fun initData() {
        //mainViewModel.getMainRepository()
        lifecycleScope.launch(Dispatchers.IO) {
            mAdapter.setNewData(
                mutableListOf<MainData>().apply {
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

                })
        }
    }

    override fun initView() {
        initRecyclerView()
        val registerForActivityResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode != AppCompatActivity.RESULT_OK) {
                    return@registerForActivityResult
                }
                val images = it.data?.getStringArrayListExtra("Data")
                val content = it.data?.getStringExtra("content")
                val mutableListOf = mutableListOf<MainData>().apply {
                    add(MainData(AppConstant.Constant.ITEM_MAIN_TITLE))
                    if (!content.isNullOrEmpty()) {
                        add(MainData(AppConstant.Constant.ITEM_MAIN_CONTENT_TEXT).apply {
                            dynamicContent = content
                        })
                    }
                    if (!images.isNullOrEmpty()) {
                        add((MainData(AppConstant.Constant.ITEM_MAIN_CONTENT_IMAGE).apply {
                            imageList = images
                        }))
                    }


                    add(MainData(AppConstant.Constant.ITEM_MAIN_IDENTIFICATION))
                }
                mAdapter.addData(0, mutableListOf)
                recyclerView.scrollToPosition(0)
            }
        commonToolBar.ivBack.let {

            val mLayoutParams = it.layoutParams as ConstraintLayout.LayoutParams
            it.setPadding(
                0f.dip2px(requireContext()),
                0f.dip2px(requireContext()),
                0f.dip2px(requireContext()),
                0f.dip2px(requireContext())
            )
            mLayoutParams.marginStart = 15f.dip2px(requireContext())
            it.shapeAppearanceModel = ShapeAppearanceModel.builder()
                .setAllCornerSizes(ShapeAppearanceModel.PILL)
                .build()
        }

        val userInfo = getUserInfo()
        Glide.with(this)
            .load(userInfo?.userImage)
            .into(commonToolBar.ivBack)
        commonToolBar.imageAddCallBack = object : CommonToolBar.ImageAddCallBack {
            override fun imageAddClickListener(view: View) {

//                GlobalScope.launch(Dispatchers.IO) {
//                    BaseMAppDatabase.instance.imageTypeDao()
//                        .insertData(mutableListOf<ImageTypeData>().apply {
//                            add(ImageTypeData(null, "推荐", "1", ""))
//                            add(ImageTypeData(null, "动漫", "2", ""))
//                            add(ImageTypeData(null, "二次元", "3", ""))
//                            add(ImageTypeData(null, "伤感", "4", ""))
//                            add(ImageTypeData(null, "风景", "5", ""))
//                            add(ImageTypeData(null, "治愈", "6", ""))
//                            add(ImageTypeData(null, "小清新", "7", ""))
//                        })
//
//                    val allData = BaseMAppDatabase.instance.imageTypeDao().getAllData()
//                    for (i in allData){
//                        Log.i(TAG, "imageAddClickListener: ${i.toString()}")
//                    }
//                }

                registerForActivityResult.launch(
                    Intent(requireContext(), AddDynamicActivity::class.java)
                )
            }
        }
        commonToolBar.imageBackCallBack = object : CommonToolBar.ImageBackCallBack {
            override fun imageBackClickListener(view: View) {
                (activity as MainActivity).drawerLayout.openDrawer(GravityCompat.START)
            }

        }


    }

    override fun initUIChangeLiveData(): UIChangeLiveData? {
        return mainViewModel.uC
    }

    override fun initViewModel() {
        getMainComponent().inject(this)
        mainViewModel = getViewModel(mainViewModelFactory, MainViewModel::class.java)

    }


    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
//        mainViewModel.sMutableLiveData.observe(this, Observer {
//            recyclerView.adapter = MAdapter(R.layout.item_title, it)
//        })

        mAdapter = MAdapter(mutableListOf).apply {
            setOnItemChildClickListener { adapter, view, position ->
                when (view.id) {
                    R.id.siv_img -> {
                        buildARouter(AppConstant.RoutePath.OTHER_PERSON_INFO_ACTIVITY).navigation()
                    }
                    R.id.iv_fabulous -> {
                        val ivFabulous = adapter.getViewByPosition(
                            recyclerView,
                            position,
                            R.id.iv_fabulous
                        ) as LottieAnimationView
                        ivFabulous.playAnimation()
                    }
                    R.id.iv_comment -> {
                        val ivComment = adapter.getViewByPosition(
                            recyclerView,
                            position,
                            R.id.iv_comment
                        ) as LottieAnimationView
                        ivComment.playAnimation()
                    }
                    R.id.iv_forward -> {
                        val ivForward = adapter.getViewByPosition(
                            recyclerView,
                            position,
                            R.id.iv_forward
                        ) as LottieAnimationView
                        ivForward.playAnimation()
                    }
                }
            }

            setOnItemClickListener { adapter, view, position ->
                buildARouter(AppConstant.RoutePath.DYNAMIC_DETAIL_ACTIVITY).navigation()
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
                        if (GSYVideoManager.isFullState(requireActivity())) {
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
                                ImageViewPagerDialog(requireContext(), item.imageList!!, position)
                            XPopup.Builder(requireContext()).asCustom(imageViewPagerDialog).show()
                        }
                    }
                }
                AppConstant.Constant.ITEM_MAIN_IDENTIFICATION -> {

                    helper.addOnClickListener(R.id.iv_fabulous)
                        .addOnClickListener(R.id.iv_comment)
                        .addOnClickListener(R.id.iv_forward)

                }
                AppConstant.Constant.ITEM_MAIN_CONTENT_VIDEO -> {
                    var gsyVideoPlayer = helper.getView<StandardGSYVideoPlayer>(R.id.detailPlayer)

                    gsyVideoPlayer.thumbImageView = ImageView(requireContext()).apply {
                        scaleType = ImageView.ScaleType.CENTER_CROP
                        setImageResource(R.drawable.iv_bear)
                    }
                    gsyVideoPlayer.setUpLazy(item.videoUrl, true, null, null, "这是title")
                    gsyVideoPlayer.titleTextView.visibility = View.GONE
                    gsyVideoPlayer.backButton.visibility = View.GONE
                    gsyVideoPlayer.fullscreenButton
                        .setOnClickListener {
                            gsyVideoPlayer.startWindowFullscreen(
                                context,
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

    override fun onDestroyView() {
        super.onDestroyView()
        lifecycleScope.cancel()
    }
}